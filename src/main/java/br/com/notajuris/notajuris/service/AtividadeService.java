package br.com.notajuris.notajuris.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.model.atendimento.Atendimento;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtendimento;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtividade;
import br.com.notajuris.notajuris.model.atividade.TipoAtividade;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.AtividadeRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class AtividadeService {
    
    @Autowired
    AtividadeRepository repository;

    //@Autowired
    //AtendimentoService atendimentoService;

    public Atividade save(AtividadeDto dto, Usuario usuario){
        //pega o dto e transforma em entidade
        Atividade entity = Atividade.toEntity(dto, usuario);
        //salva a atividade no banco
        Atividade save = repository.save(entity);

        //se atividade for do tipo atendimento, salvar detalhes do atendimento
        if(save.getTipo().equals(TipoAtividade.ATENDIMENTO)){
            //salva atendimento e retorna junto com os detalhes
            //Atendimento atendimento = atendimentoService.save( (DetalhesAtendimento) dto.detalhes(), save);
        }
        //TODO sistema de anexo

        //retorna a atividade salva
        return save;
    }

    public Set<Atividade> getAtividadesById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAtividadesById'");
    }
}
