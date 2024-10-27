package br.com.notajuris.notajuris.unittests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.cargo.CargoNome;
import br.com.notajuris.notajuris.model.usuario.TurnoAluno;
import br.com.notajuris.notajuris.model.usuario.Usuario;

@SpringBootTest
public class AtividadeServiceTest {

    Usuario usuarioTeste;

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

    }

    @Test
    @DisplayName("deve criar uma atividade do tipo PLANTAO, PENDENTE")
    public void createAtividadePlantao(){
        //quando receber um AtividadeDto

        //entao converte o dto em uma atividade, chama o serviço e retorna uma atividade do tipo PLANTAO e PENDENTE
    }
}
