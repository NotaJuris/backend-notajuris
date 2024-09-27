package br.com.notajuris.notajuris.model.usuario;

public record UsuarioResponseDto(
    String nome,
    String matricula,
    Integer periodo,
    String email,
    String telefone,
    String cargo
) {
    
}
