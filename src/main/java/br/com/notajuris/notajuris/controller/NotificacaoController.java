package br.com.notajuris.notajuris.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.notajuris.notajuris.model.notificacao.Notificacao;
import br.com.notajuris.notajuris.model.notificacao.NotificacaoDto;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.service.NotificaoService;
import br.com.notajuris.notajuris.service.UsuarioService;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/v1/notificacao")
public class NotificacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificaoService notificacaoService;

    @Transactional
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<NotificacaoDto>> getNotificacaoByUsuario(@PathVariable Integer usuarioId){

        //procura o usuário pelo id
        Usuario usuario = usuarioService.getById(usuarioId);

        //procura as atividades
        List<Notificacao> notificacaoList = notificacaoService.findByDestinatario(usuario);

        //mapeia para DTO
        List<NotificacaoDto> dtoList = notificacaoList.stream()
            .map(
                    notificacao -> new NotificacaoDto(
                        notificacao.getId(),
                        notificacao.getTitulo(),
                        notificacao.getMensagem(),
                        notificacao.getVisto()
                    )
                )
            .toList();

        return ResponseEntity.ok(dtoList);

        
    }

}
