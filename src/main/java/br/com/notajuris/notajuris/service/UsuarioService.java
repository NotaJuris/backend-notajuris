package br.com.notajuris.notajuris.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    public Usuario getById(Integer id){
        Optional<Usuario> usuario = repository.findById(id);

        return usuario.orElseThrow(() -> new BusinessException("Usuario nao encontrado ou nao existe", HttpStatus.NOT_FOUND));
    }

    public Usuario getByMatricula(String matricula){

        Optional<Usuario> usuario = repository.findByMatricula(matricula);
        if(usuario.isEmpty()){
            return null;
        }
        return usuario.get();

    }
    
}
