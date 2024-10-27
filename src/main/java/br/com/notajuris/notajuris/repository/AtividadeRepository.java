package br.com.notajuris.notajuris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.notajuris.notajuris.model.atividade.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    
}