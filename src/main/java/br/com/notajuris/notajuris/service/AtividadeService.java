package br.com.notajuris.notajuris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.AtividadeRepository;

@Service
public class AtividadeService {
    
    @Autowired
    AtividadeRepository repository;

    public Atividade save(AtividadeDto dto, Usuario usuario){
        //pega o dto e transforma em entidade
        Atividade entity = Atividade.toEntity(dto, usuario);
    }
}
