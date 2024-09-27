package br.com.notajuris.notajuris.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioResponseDto;
import br.com.notajuris.notajuris.model.usuario.Usu;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioContoller {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/me")
    public UsuarioResponseDto getCurrentUsuario(@RequestHeader("Authorization") String token) {

        //converte o token
        String usuarioId = tokenService.validateToken(token);
        //procura o usuario
        Usuario usuario = usuarioService.getById(Integer.parseInt(usuarioId));
        //retorna o usuario
        return new UsuarioResponseDto(
            usuario.getNome(),
            usuario.getMatricula(),
            usuario.getPeriodo().toString(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getCargo().toString()
        );
    }
    
    
}
