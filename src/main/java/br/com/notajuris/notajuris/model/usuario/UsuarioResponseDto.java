package br.com.notajuris.notajuris.model.usuario;

public record UsuarioResponseDto(
    Integer id,
    String nome,
    String matricula,
    Integer periodo,
    String email,
    String telefone,
    String cargo
) {
    
}
