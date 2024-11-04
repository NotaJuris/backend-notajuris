package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.notajuris.notajuris.model.Endereco;
import br.com.notajuris.notajuris.model.EstadoCivil;
import br.com.notajuris.notajuris.model.atendimento.Atendimento;
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

	@JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty(value = "data_nascimento")
    private LocalDate dataNascimento;

    private String naturalidade;

    private String cpf;

    private String rg;

    private Endereco endereco;

    private String celular;

    private List<String> filiacao;

    private String observacoes;

    public DetalhesAtendimento toDetalhe(Atendimento atendimento) {
        
        return new DetalhesAtendimento(
            atendimento.getNome(),
            atendimento.getEstadoCivil(),
            atendimento.getProfissao(),
            atendimento.getDataNascimento(),
            atendimento.getNaturalidade(),
            atendimento.getCpf(),
            atendimento.getRg(),
            atendimento.getEndereco(),
            atendimento.getCelular(),
            atendimento.getFiliacao(),
            atendimento.getObservacoes()
        );
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + ((profissao == null) ? 0 : profissao.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((filiacao == null) ? 0 : filiacao.hashCode());
		result = prime * result + ((observacoes == null) ? 0 : observacoes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalhesAtendimento other = (DetalhesAtendimento) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (estadoCivil != other.estadoCivil)
			return false;
		if (profissao == null) {
			if (other.profissao != null)
				return false;
		} else if (!profissao.equals(other.profissao))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (filiacao == null) {
			if (other.filiacao != null)
				return false;
		} else if (!filiacao.equals(other.filiacao))
			return false;
		if (observacoes == null) {
			if (other.observacoes != null)
				return false;
		} else if (!observacoes.equals(other.observacoes))
			return false;
		return true;
	}

    

}
