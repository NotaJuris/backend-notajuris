package br.com.notajuris.notajuris.infra.bean;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.cargo.CargoNome;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.CargoRepository;
import br.com.notajuris.notajuris.repository.UsuarioRepository;
import br.com.notajuris.notajuris.service.UsuarioService;

@Component
public class ApplicationStart implements ApplicationRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(usuarioRepository.findByMatricula("03341106").isEmpty()){

            Cargo cargo = cargoRepository.findById(4).get();

            Usuario usuario = Usuario.builder()
                                .id(1)
                                .nome("Lucas Lucena Calixto")
                                .matricula("03341106")
                                .email("lucaslucenacalixto1@gmail.com")
                                .senha("$2a$12$sz7WvAvU3R6ClM7jeRzBCOud5XSaP/dNS62h99b4lyPojcoOYFufK")
                                .periodo(null)
                                .telefone("83981698068")
                                .cargo(cargo)
                                .ativo(true)
                                .build();

            usuarioRepository.save(usuario);
        }
    }

}
