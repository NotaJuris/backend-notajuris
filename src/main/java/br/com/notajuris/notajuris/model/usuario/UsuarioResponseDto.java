package br.com.notajuris.notajuris.model.usuario;

public record UsuarioResponseDto(
    String nome,
    String matricula,
    String periodo,
    String email,
    String telefone,
    String cargo
) {
    
}
