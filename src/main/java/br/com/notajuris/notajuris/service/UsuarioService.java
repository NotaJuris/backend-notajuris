package br.com.notajuris.notajuris.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.model.usuario.UsuarioRegistroDto;
import br.com.notajuris.notajuris.repository.CargoRepository;
import br.com.notajuris.notajuris.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario getById(Integer id){
        Optional<Usuario> usuario = repository.findById(id);

        return usuario.orElseThrow(() -> new BusinessException("Usuario nao encontrado ou nao existe", HttpStatus.NOT_FOUND));
    }

    public Usuario getByMatricula(String matricula){

        Optional<Usuario> usuario = repository.findByMatricula(matricula);
        
        return usuario.orElse(null);

    }

    public Usuario save(UsuarioRegistroDto usuarioDto){

        //verifica se usuario já existe pela matricula
        Optional<Usuario> byMatricula = repository.findByMatricula(usuarioDto.matricula());

        if (byMatricula.isPresent()){
            throw new BusinessException("Já existe um usuário com esta matricula", HttpStatus.BAD_REQUEST);
        }
        //transforma dto em entidade
        //pega o cargo do banco para salvar
        Optional<Cargo> cargoOpt = cargoRepository.findByNome(usuarioDto.cargo());
        Usuario usuario = Usuario.builder()
            .nome(usuarioDto.nome())
            .matricula(usuarioDto.matricula())
            .telefone(usuarioDto.telefone())
            .periodo(usuarioDto.periodo())
            .cargo(cargoOpt.get())
            .turno(usuarioDto.turno())
            .email(usuarioDto.email())
            .senha(passwordEncoder.encode(usuarioDto.senha()))
            .ativo(true)
            .build();
        //salva entidade
        Usuario save = repository.save(usuario);
        //retorna entidade salva
        return save;
    }
    
}
