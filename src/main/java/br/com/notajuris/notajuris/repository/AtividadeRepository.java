package br.com.notajuris.notajuris.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.usuario.Usuario;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    
    Optional<List<Atividade>> findByUsuario(Usuario usuario);
}