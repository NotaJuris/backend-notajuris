package br.com.notajuris.notajuris.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.atendimento.Atendimento;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtendimento;
import br.com.notajuris.notajuris.model.atividade.StatusAtividade;
import br.com.notajuris.notajuris.model.atividade.TipoAtividade;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.AtividadeRepository;
import br.com.notajuris.notajuris.service.NotificaoService;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class AtividadeService {

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    AtividadeRepository repository;

    @Autowired
    AtendimentoService atendimentoService;

    @Autowired
    NotificaoService notificacaoService;

    //@Autowired
    //AtendimentoService atendimentoService;

    public Atividade save(AtividadeDto dto, Usuario usuario){

        if(dto.semestre() == null){
            throw new BusinessException(
                "informe o semestre em que a atividade foi feita",
                HttpStatus.BAD_REQUEST
            );
        }

        //pega o dto e transforma em entidade
        Atividade entity = Atividade.toEntity(dto, usuario);
        //salva a atividade no banco
        Atividade save = repository.save(entity);

        //se atividade for do tipo atendimento, salvar detalhes do atendimento
        if(save.getTipo().equals(TipoAtividade.ATENDIMENTO)){

            if(dto.hora_atividade() == null){
                throw new BusinessException(
                    "Para criar uma atividade é necessário informar a hora em que o atendimento ocorreu",
                     HttpStatus.BAD_REQUEST);
            }
            //salva atendimento e retorna junto com os detalhes
            Atendimento atendimento = atendimentoService.save( (DetalhesAtendimento) dto.detalhes(), save);
            //transforma atendimento em detalhe novamente
            DetalhesAtendimento detalheAtendimento = new DetalhesAtendimento(
                atendimento.getNome(), 
                atendimento.getEstadoCivil(), 
                atendimento.getProfissao(), 
                atendimento.getDataNascimento(), 
                atendimento.getNaturalidade(), 
                atendimento.getCpf(), 
                atendimento.getRg(), 
                atendimento.getEndereco(), 
                atendimento.getCelular(), 
                atendimento.getFiliacao(), 
                atendimento.getObservacoes());

            //adiciona ao campo detalhes da classe atividade
            save.setDetalhes(detalheAtendimento);
        }
        //TODO sistema de anexo
        //retorna a atividade salva
        return save;
    }

    public List<Atividade> getAtividadesByUsuarioId(Integer usuarioId) {
        //verifica se o usuario existe
        Usuario usuario = usuarioService.getById(usuarioId);
        if(usuario != null){
            //se existir, procura todas as atividades e retorna um set de atividades
            Optional<List<Atividade>> atividades = repository.findByUsuario(usuario);
            
            return atividades.orElse(new ArrayList<Atividade>());

        } else {
            throw new BusinessException("usuario nao existe", HttpStatus.NOT_FOUND);
        }
    }

    public List<Atividade> getAtividadesByUsuarioId(Integer usuarioId, String semestre) {
        //verifica se o usuario existe
        Usuario usuario = usuarioService.getById(usuarioId);
        if(usuario != null){

            //se existir, procura todas as atividades e retorna um set de atividades
            Optional<List<Atividade>> atividades = repository.findByUsuarioAndSemestre(usuario, semestre);
            
            return atividades.orElse(new ArrayList<Atividade>());

        } else {
            throw new BusinessException("usuario nao existe", HttpStatus.NOT_FOUND);
        }
    }

    public List<Atividade> getAtividadesByUsuarioId(Integer usuarioId, StatusAtividade status) {
        //verifica se o usuario existe
        Usuario usuario = usuarioService.getById(usuarioId);
        if(usuario != null){

            //se existir, procura todas as atividades e retorna um set de atividades
            Optional<List<Atividade>> atividades = repository.findByUsuarioAndStatus(usuario, status);
            
            return atividades.orElse(new ArrayList<Atividade>());

        } else {
            throw new BusinessException("usuario nao existe", HttpStatus.NOT_FOUND);
        }
    }

    public List<Atividade> getAtividadesByUsuarioId(Integer usuarioId, StatusAtividade status, String semestre) {
        //verifica se o usuario existe
        Usuario usuario = usuarioService.getById(usuarioId);
        if(usuario != null){

            //se existir, procura todas as atividades e retorna um set de atividades
            Optional<List<Atividade>> atividades = repository.findByUsuarioAndStatusAndSemestre(usuario, status, semestre);
            
            return atividades.orElse(new ArrayList<Atividade>());

        } else {
            throw new BusinessException("usuario nao existe", HttpStatus.NOT_FOUND);
        }
    }

    public List<Atividade> getAllAtividades(){
        
        List<Atividade> atividades = repository.findAll();

        return atividades;

    }

    //solicita reenvio
    public boolean solicitaReenvio(Integer atividadeId, String mensagem){
        //pega a atividade
        Optional<Atividade> atividadeOpt = repository.findById(atividadeId);

        if(atividadeOpt.isEmpty()){
            throw new BusinessException("atividade inexistente", HttpStatus.NOT_FOUND);
        } else {
            //atualiza o status para reenvio
            Atividade atividade = atividadeOpt.get();
            atividade.setStatus(StatusAtividade.REENVIO);

            //salva no banco
            repository.save(atividade);
            //solicita envio de notificacao para o usuario
            notificacaoService.sendNotification("SOLICITAÇÃO DE REENVIO", mensagem, atividade.getUsuario());
        }
        return true;
    }

    //reenvia atividade
    public boolean reenviaAtividade(Integer atividadeId, AtividadeDto atividadeAtualizada){
        //pesquisa atividade pelo id
        Optional<Atividade> atividadeOpt = repository.findById(atividadeId);

        if(atividadeOpt.isEmpty()){
            throw new BusinessException("Atividade inexistente", HttpStatus.NOT_FOUND);
        }

        //verifica campos atualizados e os atualiza
        Atividade atividade = atividadeOpt.get();

        if(!Objects.equals(atividade.getStatus(), StatusAtividade.REENVIO)){
            throw new BusinessException("Atividade não é elegível para reenvio", HttpStatus.BAD_REQUEST);
        }

        if(!Objects.equals(atividadeAtualizada.tipo(), atividade.getTipo())){
            atividade.setTipo(atividadeAtualizada.tipo());
            System.out.println("tipo atualizado");
        }

        if(!Objects.equals(atividadeAtualizada.descricao(), atividade.getDescricao())){
            atividade.setDescricao(atividadeAtualizada.descricao());
            System.out.println("descricao atualizado");
        }

        if(!Objects.equals(atividadeAtualizada.data_atividade(), atividade.getDataAtividade())){
            atividade.setDataAtividade(atividadeAtualizada.data_atividade());
            System.out.println("data_atividade atualizado");
        }

        if(!Objects.equals(atividadeAtualizada.hora_atividade(), atividade.getHoraAtividade())){
            atividade.setHoraAtividade(atividadeAtualizada.hora_atividade());
            System.out.println("hora_atividade atualizado");
        
        }
        
        if(!Objects.equals(atividadeAtualizada.carga_horaria(), atividade.getCargaHoraria())){
            atividade.setCargaHoraria(atividadeAtualizada.carga_horaria());
            System.out.println("carga_horaria atualizado");
        }

        if(!Objects.equals(atividadeAtualizada.semestre(), atividade.getSemestre())){
            atividade.setSemestre(atividadeAtualizada.semestre());
            System.out.println("semestre atualizado");
        }
        
        if(!Objects.equals(atividadeAtualizada.detalhes(), atividade.getDetalhes())){
            atividade.setDetalhes(atividadeAtualizada.detalhes());
            System.out.println("detalhes atualizado");
        }

        atividade.setStatus(StatusAtividade.PENDENTE);

        //salva no banco
        repository.save(atividade);

        notificacaoService.sendNotification("REENVIO DE ATIVIDADE", "Sua atividade foi reenviada com suceso!", atividade.getUsuario());
        return true;
    }

    public boolean changeStatus(Integer atividadeId, StatusAtividade status){
        
        //pesquisa se a atividade existe
        Optional<Atividade> atividadeOpt = repository.findById(atividadeId);
        //se nao existir, lança erro
        if(atividadeOpt.isEmpty()){
            throw new BusinessException("Atividade não existe", HttpStatus.NOT_FOUND);
        }
        //se existir, muda o status
        Atividade atividade = atividadeOpt.get();
        atividade.setStatus(status);
        repository.save(atividade);

        notificacaoService.sendNotification("ATUALIZAÇÃO DE STATUS", "Sua atividade teve o status alterado para: "+status, atividade.getUsuario());
        //retorna verdadeiro
        return true;
    }
}
