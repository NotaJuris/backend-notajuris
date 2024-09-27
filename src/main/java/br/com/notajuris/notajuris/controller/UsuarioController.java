package br.com.notajuris.notajuris.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioResponseDto;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/me")
    public UsuarioResponseDto getCurrentUsuario(@RequestHeader("Authorization") String token) {

        //converte o token
        token = token.replace("Bearer ", "");
        System.out.println(token);
        String usuarioId = tokenService.validateToken(token);
        //procura o usuario
        Usuario usuario = usuarioService.getById(Integer.parseInt(usuarioId));
        //retorna o usuario
        return new UsuarioResponseDto(
            usuario.getNome(),
            usuario.getMatricula(),
            usuario.getPeriodo(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getCargo().getNome().toString()
        );
    }
    
    
}
