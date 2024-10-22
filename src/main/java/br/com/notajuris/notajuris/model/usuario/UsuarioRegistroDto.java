package br.com.notajuris.notajuris.model.usuario;

import br.com.notajuris.notajuris.model.cargo.CargoNome;

public record UsuarioRegistroDto(
    String nome,
    String matricula,
    String telefone,
    Integer periodo,
    CargoNome cargo,
    TurnoAluno turno,
    String email,
    String senha
) {
    
}
