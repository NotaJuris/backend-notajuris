package br.com.notajuris.notajuris.unittests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import br.com.notajuris.notajuris.NotajurisApplication;
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
import br.com.notajuris.notajuris.service.UsuarioService;

@SpringBootTest
@ContextConfiguration(classes = NotajurisApplication.class)
public class AtividadeServiceTest {

    @MockBean
    UsuarioService usuarioService;

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
    @DisplayName("deve retornar uma lista de atividades ao id 103 do usuario")
    public void getAtividadesCurrentUsuario(){

        List<Atividade> atividadesSet = List.of(
            Atividade.builder()
            .id(1)
            .tipo(TipoAtividade.PLANTAO)
            .cargaHoraria(6)
            .dataAtividade(LocalDate.now())
            .descricao("plantao")
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(null)
            .semestre("2025.1")
            .build(),
            Atividade.builder()
            .id(2)
            .tipo(TipoAtividade.AUDIENCIA)
            .cargaHoraria(3)
            .dataAtividade(LocalDate.now())
            .descricao("audiencia")
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(null)
            .semestre("2025.1")
            .build()
        );

        Mockito.when(usuarioService.getById(103)).thenReturn(usuarioTeste);
        Mockito.when(atividadeRepository.findByUsuario(usuarioTeste)).thenReturn(Optional.of(atividadesSet));
        //quando receber o id de um usuário
        List<Atividade> atividades = atividadeService.getAtividadesByUsuarioId(usuarioTeste.getId());

        //entao retorna lista de atividades referentes a esse usuario
        atividades.stream().forEach(
            atividade -> {
                System.out.println(atividade.getTipo());
                Assertions.assertEquals(atividade.getUsuario(), usuarioTeste);
            }
        );
    }

    @Test
    @DisplayName("deve retornar uma lsita de atividades do usuario 103 e de status PENDENTE")
    public void getAtividadesByIdAndSemester(){
        List<Atividade> atividadesTeste = List.of(
            Atividade.builder()
            .id(2)
            .tipo(TipoAtividade.PLANTAO)
            .cargaHoraria(3)
            .dataAtividade(LocalDate.now())
            .descricao("audiencia")
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(null)
            .semestre("2025.1")
            .build(),
            Atividade.builder()
            .id(2)
            .tipo(TipoAtividade.AUDIENCIA)
            .cargaHoraria(3)
            .dataAtividade(LocalDate.now())
            .descricao("audiencia")
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(null)
            .semestre("2025.1")
            .build(),
            Atividade.builder()
            .id(2)
            .tipo(TipoAtividade.MEDIACAO)
            .cargaHoraria(3)
            .dataAtividade(LocalDate.now())
            .descricao("audiencia")
            .horaAtividade(LocalTime.now())
            .status(StatusAtividade.PENDENTE)
            .usuario(usuarioTeste)
            .ativo(true)
            .detalhes(null)
            .semestre("2025.1")
            .build()
        );

        //quando receber o id do usuario e o código do semestre
        String semestre = "2025.1";
        Integer usuarioId = usuarioTeste.getId();

        //Mocks
        Mockito.when(usuarioService.getById(usuarioId)).thenReturn(usuarioTeste);

        Mockito.when(atividadeRepository.findByUsuarioAndSemestre(usuarioTeste, semestre)).thenReturn(Optional.of(atividadesTeste));

        //pesquisar pelo id e pelo semestre
        List<Atividade> atividades = atividadeService.getAtividadesByUsuarioId(usuarioId, semestre);

        //retornar uma lista de atividades que o semestre e o código passado coincidam
        atividades.stream()
            .forEach( atividade -> {
                Assertions.assertEquals(usuarioTeste, atividade.getUsuario());
                Assertions.assertEquals(semestre, atividade.getSemestre());;
            });
    }

    @test
    @DisplayName("deve retornar uma lista de atividades do usuário 103 e de status ACEITO")
    public void getAtividadesByIdAndStatus(){}

    @Test
    @DisplayName("deve retornar uma lista de atividade do usuário 103, de status aceito e do semestre 2025.1")
    public void getAtividadesByIsAndStatusAndSemestre(){}

}
