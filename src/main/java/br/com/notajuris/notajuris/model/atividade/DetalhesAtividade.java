package br.com.notajuris.notajuris.model.atividade;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo",
    visible = false
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DetalhesAtendimento.class, name = "ATENDIMENTO")
})
public class DetalhesAtividade {
    private TipoAtividade tipo;
}