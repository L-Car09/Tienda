package com.tienda.controller;


import com.tienda.domain.Constante;
import com.tienda.domain.Rol;
import com.tienda.domain.Role;
import com.tienda.domain.Usuario;
import com.tienda.service.ConstanteService;
import com.tienda.service.FirebaseStorageService;
import com.tienda.service.RolService;
import com.tienda.service.RoleService;
import com.tienda.service.UsuarioService;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario_role")
public class UsuarioRoleController {
    
    @Autowired
    private UsuarioService usuarioService; 
    @Autowired
    private RoleService roleService; 
    @Autowired
    private RolService rolService; 
    
    @GetMapping("/asignar")
    public String asignar(Usuario usuario, Model model) {       
        if (usuario==null){
            usuario = new Usuario();
        }
        usuario = usuarioService.getUsuario(usuario);
        if (usuario!=null){
            model.addAttribute("usuario", usuario);
            
            var lista = roleService.getRoles();
            ArrayList<String> rolesDisponibles = new ArrayList<>();
            for (Role r : lista){
                rolesDisponibles.add(r.getRol());
            }
            
            var rolesAsignados = usuario.getRoles();
            
            for (Rol r : rolesAsignados){
                rolesDisponibles.remove(r.getNombre());
            }
            
            model.addAttribute("rolesAsignados", rolesAsignados);
            model.addAttribute("rolesDisponibles", rolesDisponibles);
            model.addAttribute("idUsuario", usuario.getIdUsuario());
            model.addAttribute("username", usuario.getUsername());
        }       
        return "/usuario_role/asignar";
    }
    
    @GetMapping("/agregar")
    public String agregar (Model model, Rol rol, Usuario usuario){
        rolService.save(rol);
        return "redirect:/usuario_role/asignar?username="+usuario.getUsername();
    }
    
    @GetMapping("/eliminar")
    public String eliminar (Model model, Rol rol, Usuario usuario){
        rolService.delete(rol);
        return "redirect:/usuario_role/asignar?username="+usuario.getUsername();
    } 
}
