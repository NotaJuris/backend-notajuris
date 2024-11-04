package br.com.notajuris.notajuris.unittests;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.notajuris.notajuris.model.Endereco;
import br.com.notajuris.notajuris.model.EstadoCivil;
import br.com.notajuris.notajuris.model.atendimento.Atendimento;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.AtividadeDto;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtendimento;
import br.com.notajuris.notajuris.model.atividade.StatusAtividade;
import br.com.notajuris.notajuris.model.atividade.TipoAtividade;
import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.cargo.CargoNome;
import br.com.notajuris.notajuris.model.usuario.TurnoAluno;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.AtividadeRepository;
import br.com.notajuris.notajuris.service.AtendimentoService;
import br.com.notajuris.notajuris.service.AtividadeService;

@SpringBootTest
public class AtividadeServiceTest {

    @MockBean
    AtividadeRepository atividadeRepository;

    @MockBean
    AtendimentoService atendimentoService;

    @Autowired
    @InjectMocks
    AtividadeService atividadeService;


    static Usuario usuarioTeste;
    static Atividade atividadeTeste;
    static Atividade atividadeAtendimentoTeste;

    @BeforeAll
    public static void initTest(){
        
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

        atividadeAtendimentoTeste = Atividade.builder()
            .id(104)
            .tipo(TipoAtividade.ATENDIMENTO)
            .cargaHoraria(6)
            .dataAtividade(LocalDate.now())
            .descricao(null)
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(new DetalhesAtendimento("Maria das Dores Silva Machado",
            EstadoCivil.SEPARADO,
            "costureira",
            LocalDate.of(1998, 10, 5),
            "Souza",
            "12345678910",
            "1234567",
            new Endereco("Rua 1", "45", "Bairro A", "Cidade A", "Paraíba", null),
            "83999999999",
            null,
            null))
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
            6,
            null
        );

        Mockito.when(atividadeRepository.save(Mockito.any(Atividade.class))).thenReturn(atividadeTeste);

        //entao chama o serviço e retorna uma atividade do tipo PLANTAO e PENDENTE
        Atividade atividade = atividadeService.save(dto, usuarioTeste);

        Assertions.assertTrue(atividade.getTipo().equals(TipoAtividade.PLANTAO));
        Assertions.assertTrue(atividade.getStatus().equals(StatusAtividade.PENDENTE));
        Assertions.assertEquals(atividade.getUsuario(), usuarioTeste);
    }

    @Test
    @DisplayName("deve retornar uma atividade do tipo ATENDIMENTO")
    public void shouldCreateAtendimento(){
        //quando receber um dto e uma atividade
        AtividadeDto dto = new AtividadeDto(
            TipoAtividade.ATENDIMENTO,
            null,
            LocalDate.now(),
            LocalTime.now(),
            6,
            new DetalhesAtendimento("Maria das Dores Silva Machado",
            EstadoCivil.SEPARADO,
            "costureira",
            LocalDate.of(1998, 10, 5),
            "Souza",
            "12345678910",
            "1234567",
            new Endereco("Rua 1", "45", "Bairro A", "Cidade A", "Paraíba", null),
            "83999999999",
            null,
            null)
        );

        Atendimento atendimentoTeste = Atendimento.detalhesToEntity((DetalhesAtendimento) dto.detalhes(), atividadeAtendimentoTeste);

        Mockito.when(atividadeRepository.save(Mockito.any(Atividade.class))).thenReturn(atividadeAtendimentoTeste); //mock atividade
        Mockito.when(atendimentoService.save(
            Mockito.any(DetalhesAtendimento.class),
            Mockito.any(Atividade.class)
        )).thenReturn(atendimentoTeste);

        Atividade atendimento = atividadeService.save(dto, usuarioTeste);

        System.out.println(atendimento.getDetalhes());
        System.out.println(dto.detalhes());

        //entao chama o serviço e retorna uma atividade do tipo ATENDIMENTO e pertencente ao usuarioTeste
        Assertions.assertEquals(atendimento.getTipo(), TipoAtividade.ATENDIMENTO);
        Assertions.assertEquals(atendimento.getDetalhes(), dto.detalhes());
        Assertions.assertEquals(atendimento.getUsuario(), usuarioTeste);

    }

    @Test
    @DisplayName("deve retornar uma lista de atividades referente a um usuário teste")
    public void getAtividadesCurrentUsuario(){

        /*Set<Atividade> atividadesSet = Set.of(
            atividadeTeste,
            atividadeTeste2
        );

        //quando receber o id de um usuário
        Set<Atividade> atividades = atividadeService.getAtividadesById(usuarioTeste.getId());

        //entao retorna lista de atividades referentes a esse usuario
        atividades.stream().forEach(
            atividade -> Assertions.assertEquals(atividade.getUsuario(), usuarioTeste)
        );*/
    }
}
