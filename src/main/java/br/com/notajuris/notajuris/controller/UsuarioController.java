package br.com.notajuris.notajuris.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioRegistroDto;
import br.com.notajuris.notajuris.model.usuario.UsuarioResponseDto;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDto> getCurrentUsuario(@RequestHeader("Authorization") String token) {

        //converte o token
        token = token.replace("Bearer ", "");
        Integer usuarioId = tokenService.validateToken(token);
        //procura o usuario
        Usuario usuario = usuarioService.getById(usuarioId);
        //retorna o usuario
        UsuarioResponseDto response = new UsuarioResponseDto(
            usuario.getId(),
            usuario.getNome(),
            usuario.getMatricula(),
            usuario.getPeriodo(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getCargo().getNome().toString()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUsuario(@RequestBody UsuarioRegistroDto registroDto){
        //salva o dto
        Usuario save = usuarioService.save(registroDto);
        //retorna o response
        UsuarioResponseDto response = new UsuarioResponseDto(
            save.getId(),
            save.getNome(),
            save.getMatricula(),
            save.getPeriodo(),
            save.getEmail(),
            save.getTelefone(),
            save.getCargo().getNome().toString()
        );
        return ResponseEntity.ok(response);
    }
}