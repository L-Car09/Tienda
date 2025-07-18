package com.tienda.service;

import com.tienda.domain.Rol;
import com.tienda.domain.Usuario;
import com.tienda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        //Se busca el usuario en la tabla usuarios...
        Usuario usuario = usuarioRepository.findByUsername(username);
        
        //Se valida si el username existe...
        if (usuario==null){
            //No existe...
            throw new UsernameNotFoundException(username);
        }
        
        //Si estamos acá... entonces todo bien... se encontró el usuario
        session.removeAttribute("imagenUsuario");
        session.setAttribute("imagenUsuario", usuario.getRutaImagen());
        
        //Se van a cargar los "roles" como ROLES de seguridad...
        var roles = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
            
        }
        
        //Se retorna un User con la información del usuario y roles "permisos"
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
    
}
