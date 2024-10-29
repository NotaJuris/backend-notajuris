package br.com.notajuris.notajuris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.AtividadeResponseDto;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.service.AtividadeService;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@Controller
@RequestMapping("/v1/atividades")
public class AtividadeController {
    
    @Autowired
    AtividadeService atividadeService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<AtividadeResponseDto> registraAtividade(@RequestBody AtividadeDto dto, @RequestHeader("Authorization") String token) {
        
        //procura o usuário pela token
        token = token.replace("Bearer ", "");
        Integer usuarioId = tokenService.validateToken(token);
        
        if(usuarioId != null){
            Usuario usuario = usuarioService.getById(usuarioId);
            //registra a atividade
            Atividade save = atividadeService.save(dto, usuario);

            //retorna o dto de resposta
            AtividadeResponseDto responseDto = new AtividadeResponseDto(
                save.getId(),
                save.getTipo(),
                save.getDescricao(),
                save.getDataAtividade(),
                save.getHoraAtividade(), 
                usuario.getNome(), 
                save.getStatus()
            );

            return ResponseEntity.ok(responseDto);
        } else {
            throw new BusinessException("erro ao registrar atividade", HttpStatus.NOT_FOUND);
        }
        
    }
    
}