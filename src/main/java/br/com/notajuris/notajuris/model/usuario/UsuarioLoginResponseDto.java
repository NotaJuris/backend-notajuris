package br.com.notajuris.notajuris.model.usuario;

public record UsuarioLoginResponseDto(
    String token,
    String refreshToken
) {
    
}
