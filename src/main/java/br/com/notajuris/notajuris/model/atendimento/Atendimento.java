package br.com.notajuris.notajuris.model.atendimento;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import br.com.notajuris.notajuris.model.EstadoCivil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atendimento_id")
    private Integer id;

    @Column(name = "nome_atendido", nullable = false)
    private String nome;

    @Column(name = "estadocivil_atendido", nullable = false)
    private EstadoCivil estadoCivil;

    @Column(name = "profissao_atendido", nullable = false)
    private String profissao;

    @Column(name = "data_nasc_atendido", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "naturalidade_atendido", nullable = false)
    private String naturalidade;

    @Column(name = "cpf_atendido", nullable = false)
    private String cpf;

    @Column(name = "rg_atendido", nullable = false)
    private String rg;

    @Column(name = "celular_atendido", nullable = false)
    private String celular;

    @Column(name = "filiacao_atendido", nullable = false)
    private List<String> filiacao;

    @Column(nullable = false)
    private String observacoes;

    private String endereco;

    @ColumnDefault(value = "1")
    private Boolean ativo;

}
