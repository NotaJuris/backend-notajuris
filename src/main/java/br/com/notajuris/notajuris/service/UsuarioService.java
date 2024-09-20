package br.com.notajuris.notajuris.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.notajuris.notajuris.exceptions.BusinessException;
import br.com.notajuris.notajuris.model.usuario.Usuario;
import br.com.notajuris.notajuris.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    public Usuario getById(Integer id){
        Usuario usuario = repository.getReferenceById(id);
        if(usuario == null){
            throw new BusinessException("User not found");
        }
        return usuario;
    }

    public Usuario getByMatricula(String matricula){

        Optional<Usuario> usuario = repository.findByMatricula(matricula);
        if(usuario.isEmpty()){
            throw new BusinessException("Usuario nao existe");
        }
        return usuario.get();

    }
    
}
