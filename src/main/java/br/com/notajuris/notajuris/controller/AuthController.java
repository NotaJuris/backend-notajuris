package br.com.notajuris.notajuris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notajuris.notajuris.infra.security.JwtService;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioLoginDto;
import br.com.notajuris.notajuris.model.usuario.UsuarioLoginResponseDto;
import br.com.notajuris.notajuris.service.UsuarioService;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder PasswordEncoder;

    @Autowired
    UsuarioService usuarioService;

    //login
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody UsuarioLoginDto usuarioDto){

        Usuario usuario = usuarioService.getByMatricula(usuarioDto.matricula());

        //se senhas nao coincidirem, retorna nulo e 403.
        if(!(PasswordEncoder.matches(usuarioDto.senha(), usuario.getSenha()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        //se senhas coincidirem, gerar token jwt.
        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new UsuarioLoginResponseDto(token));
    }

    //cadastro
    
}
