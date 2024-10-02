package br.com.notajuris.notajuris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioLoginDto;
import br.com.notajuris.notajuris.model.usuario.UsuarioLoginResponseDto;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    TokenService tokenService;
    
    @Autowired
    PasswordEncoder PasswordEncoder;
    
    @Autowired
    UsuarioService usuarioService;

    //login
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody UsuarioLoginDto usuarioDto){

        Usuario usuario = usuarioService.getByMatricula(usuarioDto.matricula());
        if(usuario == null){
            throw new BusinessException("Usuario nao encontrado ou nao existe", HttpStatus.NOT_FOUND);
        }

        //se senhas nao coincidirem, retorna nulo e 403.
        if(!(PasswordEncoder.matches(usuarioDto.senha(), usuario.getSenha()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        //se senhas coincidirem, gerar token jwt.
        String token = tokenService.generateToken(usuario);
        String refreshToken = tokenService.generateRefreshToken(usuario);
        return ResponseEntity.ok(new UsuarioLoginResponseDto(token, refreshToken));
    }

    //cadastro

    //refresh
    @PostMapping("{refreshToken}/refresh")
    public ResponseEntity<UsuarioLoginResponseDto> refresh(@PathVariable String refreshToken){

        Integer usuarioId = tokenService.validateRefreshToken(refreshToken);

        Usuario usuario = usuarioService.getById(usuarioId);
        String token = tokenService.generateToken(usuario);
        String newRefreshToken = tokenService.generateRefreshToken(usuario);

        return ResponseEntity.ok(new UsuarioLoginResponseDto(token, newRefreshToken));
    }
    
}
