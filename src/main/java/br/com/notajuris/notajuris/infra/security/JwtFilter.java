package br.com.notajuris.notajuris.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            String token = this.recoverToken(request);
            String usuarioId = tokenService.validateToken(token);

            if(usuarioId != null){
                Usuario usuario = usuarioService.getById(Integer.parseInt(usuarioId));
                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities())
                );
            }

            filterChain.doFilter(request, response);
            
        }

    private String recoverToken(HttpServletRequest request){
        
        String header = request.getHeader("Authorization");
        if(header == null){
            return null;
        }

        return header.replace("Bearer ", "");

    }
    
}
