package br.com.notajuris.notajuris.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.StatusAtividade;
import br.com.notajuris.notajuris.model.usuario.Usuario;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    
    Optional<List<Atividade>> findByUsuario(Usuario usuario);

    Optional<List<Atividade>> findByUsuarioAndSemestre(Usuario usuario, String semestre);

    Optional<List<Atividade>> findByUsuarioAndStatus(Usuario usuario, StatusAtividade status);

    Optional<List<Atividade>> findByUsuarioAndStatusAndSemestre(Usuario usuario, StatusAtividade status, String semestre);
}