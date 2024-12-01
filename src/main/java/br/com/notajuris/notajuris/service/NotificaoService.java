package br.com.notajuris.notajuris.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.notificacao.Notificacao;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.NotificacaoRepository;
import jakarta.transaction.Transactional;

@Service
public class NotificaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public void sendNotification(String titulo, String mensagem, Usuario usuario) {
        
        System.out.println("titulo: "+ titulo);
        System.out.println("mensagem: "+ mensagem);
        System.out.println("usuario: "+ usuario.getNome());

        //instancia notificacao
        Notificacao notificacao = Notificacao.builder()
            .titulo(titulo)
            .mensagem(mensagem)
            .destinatario(usuario)
            .visto(false)
            .build();
        
            System.out.println("notificacao obj: "+notificacao);
        //salva notificacao
        notificacaoRepository.save(notificacao);
        System.out.println("Notificação enviada");
    }

    public List<Notificacao> findByDestinatario(Usuario destinatario){

        Optional<List<Notificacao>> byDestinatario = notificacaoRepository.findByDestinatario(destinatario);

        return byDestinatario.orElseThrow(() -> new BusinessException("Usuario nao encontrado ou nao existe", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public boolean setNotificacaoVisto(Integer notificacaoId){
        //procura notificacao
        Optional<Notificacao> notificacaoOpt = notificacaoRepository.findById(notificacaoId);

        if(notificacaoOpt.isEmpty()){
            throw new BusinessException("Notificacao nao encontrada", HttpStatus.NOT_FOUND);
        }
        Notificacao notificacao = notificacaoOpt.get();

        notificacao.setVisto(true);
        notificacaoRepository.delete(notificacao);
        return true;
    }
    
    

}
