package com.tienda.controller;


import com.tienda.domain.Constante;
import com.tienda.service.ConstanteService;
import com.tienda.service.FirebaseStorageService;
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
@RequestMapping("/constante")
public class ConstanteController {
    
    @Autowired
    private ConstanteService constanteService; 
    
    @GetMapping("/listado")
    public String listado(Model model) {       
        var lista = constanteService.getConstantes();        
        model.addAttribute("constantes", lista);
        model.addAttribute("totalConstantes", lista.size());      
        return "/constante/listado";
    }
    
    @Autowired
    private MessageSource messageSource;
    
    @PostMapping("/guardar")
    public String guardar(Constante constante,
            RedirectAttributes redirectAttributes) {
        constanteService.save(constante);
        redirectAttributes.addFlashAttribute("error",messageSource.getMessage("Se realizó la actualización.", null, Locale.getDefault()));    
        return "redirect:/constante/listado";
    }
    @PostMapping("/eliminar")
    public String eliminar(Constante constante, 
            RedirectAttributes redirectAttributes) {
        constante = constanteService.getConstante(constante);
        if (constante==null){
            redirectAttributes.addFlashAttribute("error",messageSource.getMessage("constante.error01", null, Locale.getDefault()));
        }else if (false) {
            redirectAttributes.addFlashAttribute("error",messageSource.getMessage("constante.error02", null, Locale.getDefault()));
        }else if (constanteService.delete(constante)){
             redirectAttributes.addFlashAttribute("error",messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute("error",messageSource.getMessage("constante.error03", null, Locale.getDefault()));
        }
        return "redirect:/constante/listado";    
    }
    
    @PostMapping("/modificar")
    public String modificar(Constante constante, Model model) {       
        constante=constanteService.getConstante(constante);
        model.addAttribute("constante", constante);
        return "/constante/modifica";
    }
    
    
    
    
}
