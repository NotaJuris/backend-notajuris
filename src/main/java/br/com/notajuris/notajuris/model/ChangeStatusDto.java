package br.com.notajuris.notajuris.model;

import br.com.notajuris.notajuris.model.atividade.StatusAtividade;

public record ChangeStatusDto(
    StatusAtividade status
) {
}
