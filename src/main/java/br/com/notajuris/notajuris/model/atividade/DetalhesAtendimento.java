package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.notajuris.notajuris.model.Endereco;
import br.com.notajuris.notajuris.model.EstadoCivil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetalhesAtendimento extends DetalhesAtividade{
    @JsonProperty(value = "nome_atendido")
    private String nome;

    @JsonProperty(value = "estado_civil")
    private EstadoCivil estadoCivil;

    private String profissao;

    @JsonProperty(value = "data_nascimento")
    private LocalDate dataNascimento;

    private String naturalidade;

    private String cpf;

    private String rg;

    private Endereco endereco;

    private String celular;

    private List<String> filiacao;

    private String observacoes;

}
