package br.com.notajuris.notajuris.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.ChangeStatusDto;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.AtividadeResponseDto;
import br.com.notajuris.notajuris.model.atividade.StatusAtividade;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.service.AtividadeService;
import br.com.notajuris.notajuris.service.TokenService;
import br.com.notajuris.notajuris.service.UsuarioService;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                save.getStatus(),
                save.getSemestre(),
                save.getDetalhes()
            );

            return ResponseEntity.ok(responseDto);
        } else {
            throw new BusinessException("erro ao registrar atividade", HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping("")
    public ResponseEntity<List<AtividadeResponseDto>> getAllAtividades(){
        
        List<Atividade> atividades = atividadeService.getAllAtividades();

        List<AtividadeResponseDto> atividadeDtos = atividades.stream().map(
            atividade -> new AtividadeResponseDto(
                atividade.getId(),
                atividade.getTipo(),
                atividade.getDescricao(),
                atividade.getDataAtividade(),
                atividade.getHoraAtividade(), 
                atividade.getUsuario().getNome(), 
                atividade.getStatus(),
                atividade.getSemestre(),
                atividade.getDetalhes()
            )
        ).toList();

        return ResponseEntity.ok(atividadeDtos);

    }

    @GetMapping("/{usuarioId}/usuario")
    public ResponseEntity<List<AtividadeResponseDto>> getByUsuarioId(
        @PathVariable Integer usuarioId,
        @PathParam("semestre") String semestre,
        @PathParam("status") StatusAtividade status){

        List<Atividade> atividades = null;
        
        if(status != null && semestre != null){
            //pesquisa por status e por semestre
            System.out.println("semestre e status");
            atividades = atividadeService.getAtividadesByUsuarioId(usuarioId, status, semestre);
        } else if(status != null && semestre == null){
            //pesquisa por status
            System.out.println("status");
            atividades = atividadeService.getAtividadesByUsuarioId(usuarioId, status);
        } else if(status == null && semestre != null){
            //pesquisa por semestre
            System.out.println("semestre");
            atividades = atividadeService.getAtividadesByUsuarioId(usuarioId, semestre);
        } else {
            //pesquisa por id
            System.out.println("id");
            atividades = atividadeService.getAtividadesByUsuarioId(usuarioId);
        }
        
        if(atividades.isEmpty()){
            return ResponseEntity.ok(new ArrayList<AtividadeResponseDto>());
        } else {
            List<AtividadeResponseDto> dtos = atividades.stream().map(
                atividade -> new AtividadeResponseDto(
                atividade.getId(),
                atividade.getTipo(),
                atividade.getDescricao(),
                atividade.getDataAtividade(),
                atividade.getHoraAtividade(),
                atividade.getUsuario().getNome(),
                atividade.getStatus(),
                atividade.getSemestre(),
                atividade.getDetalhes()
                )
            ).toList();

            return ResponseEntity.ok(dtos);
        }
    }
    
    //marcar atividade como reenviada
    

    //reenviar atividade

    //alterar status
    @PatchMapping("/{atividadeId}/status")
    public ResponseEntity<String> changeAtividadeStatus(
        @PathVariable Integer atividadeId,
        @RequestBody ChangeStatusDto statusDto
        ){

        System.out.println("Id: "+atividadeId);
        System.out.println("status: "+statusDto.status());
        if(atividadeService.changeStatus(atividadeId, statusDto.status())){
            System.out.println("mudou o status");
            return ResponseEntity.ok("Status da atividade atualizado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Não foi possível atualizar o status da atividade.");
        }

    }

}
