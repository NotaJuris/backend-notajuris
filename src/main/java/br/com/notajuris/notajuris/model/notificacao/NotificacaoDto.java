package br.com.notajuris.notajuris.model.notificacao;

public record NotificacaoDto(
    Integer id,
    String titlo,
    String mensagem,
    boolean visto
) {
    
}
