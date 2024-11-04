package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtividadeResponseDto(
    Integer id,
    TipoAtividade tipo,
    String descricao,
    LocalDate data_atividade,
    LocalTime hora_atividade,
    String aluno,
    StatusAtividade status,
    DetalhesAtividade detalhes
) {
    
}
