package br.com.notajuris.notajuris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.notajuris.notajuris.model.notificacao.Notificacao;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    
    Optional<List<Notificacao>> findByDestinatario(Usuario destinatario);

}
