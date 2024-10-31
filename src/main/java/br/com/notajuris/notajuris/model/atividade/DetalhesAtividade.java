package br.com.notajuris.notajuris.model.atividade;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "detalhes"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DetalhesAtendimento.class, name = "ATENDIMENTO")
})
public class DetalhesAtividade {
}
