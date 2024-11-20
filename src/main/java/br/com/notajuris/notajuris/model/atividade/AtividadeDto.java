package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AtividadeDto(
    TipoAtividade tipo,
    String descricao,
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate data_atividade,
    @JsonFormat(pattern="HH:mm")
    LocalTime hora_atividade,
    Integer carga_horaria,
    String semestre,
    DetalhesAtividade detalhes
) {
    
}
