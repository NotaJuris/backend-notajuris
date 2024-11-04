package br.com.notajuris.notajuris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.model.atendimento.Atendimento;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtendimento;
import br.com.notajuris.notajuris.repository.AtendimentoRepository;
import jakarta.transaction.Transactional;

@Service
public class AtendimentoService {
    
    @Autowired
    private AtendimentoRepository repository;

    @Transactional
    public Atendimento save(DetalhesAtendimento dto, Atividade atividade){
        //recebe o dto e converte em atendimento
        Atendimento atendimento = Atendimento.detalhesToEntity(dto, atividade);

        //salva o atendimento no banco
        Atendimento save = repository.save(atendimento);
        //retorna o atendimento
        return save;
    }
}
