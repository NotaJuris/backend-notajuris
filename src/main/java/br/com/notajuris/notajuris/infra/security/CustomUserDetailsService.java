package br.com.notajuris.notajuris.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.service.UsuarioService;
import jakarta.transaction.Transactional;

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UsuarioService usuarioService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getById(Integer.parseInt(username));
        return usuario;
    }

    
    
}
