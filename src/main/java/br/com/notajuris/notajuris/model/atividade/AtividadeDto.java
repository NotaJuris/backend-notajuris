package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtividadeDto(
    TipoAtividade tipo,
    String descricao,
    LocalDate data_atividade,
    LocalTime hora_atividade,
    Integer carga_horatia
) {
    
}
