package br.com.notajuris.notajuris.unittests;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.StatusAtividade;
import br.com.notajuris.notajuris.model.atividade.TipoAtividade;
import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.cargo.CargoNome;
import br.com.notajuris.notajuris.model.usuario.TurnoAluno;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.AtividadeRepository;
import br.com.notajuris.notajuris.service.AtividadeService;

@SpringBootTest
public class AtividadeServiceTest {

    @MockBean
    AtividadeRepository atividadeRepository;

    @Autowired
    @InjectMocks
    AtividadeService atividadeService;

    Usuario usuarioTeste;
    Atividade atividadeTeste;

    @BeforeAll
    public void initTest(){
        
        usuarioTeste = Usuario.builder()
            .id(103)
            .nome("Carlos Lacerda Campos")
            .matricula("03349939")
            .email("carlos123@gmail.com")
            .senha("carlos123")
            .telefone("83999999999")
            .turno(TurnoAluno.MANHA)
            .periodo(10)
            .cargo(new Cargo(CargoNome.ALUNO))
            .ativo(true)
            .build();

        atividadeTeste = Atividade.builder()
            .id(103)
            .tipo(TipoAtividade.PLANTAO)
            .cargaHoraria(6)
            .dataAtividade(LocalDate.now())
            .descricao(null)
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .build();
    }

    @Test
    @DisplayName("deve criar uma atividade do tipo PLANTAO, PENDENTE")
    public void createAtividadePlantao(){
        //quando receber um AtividadeDto
        AtividadeDto dto = new AtividadeDto(
            TipoAtividade.PLANTAO,
            null,
            LocalDate.now(),
            LocalTime.now(),
            6
        );
        //entao chama o serviço e retorna uma atividade do tipo PLANTAO e PENDENTE
        Atividade atividade = atividadeService.save(dto, usuarioTeste);

        Assertions.assertTrue(atividade.getTipo().equals(TipoAtividade.PLANTAO));
        Assertions.assertTrue(atividade.getStatus().equals(StatusAtividade.PENDENTE));
        Assertions.assertEquals(atividade.getUsuario(), usuarioTeste);
    }
}
