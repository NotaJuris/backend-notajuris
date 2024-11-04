package br.com.notajuris.notajuris.model.atendimento;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import br.com.notajuris.notajuris.infra.bean.AesEncryptionConfig;
import br.com.notajuris.notajuris.infra.bean.EnderecoStringConverter;
import br.com.notajuris.notajuris.infra.bean.FiliacaoConverter;
import br.com.notajuris.notajuris.model.Endereco;
import br.com.notajuris.notajuris.model.EstadoCivil;
import br.com.notajuris.notajuris.model.atividade.Atividade;
import br.com.notajuris.notajuris.model.atividade.DetalhesAtendimento;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Convert(converter = AesEncryptionConfig.class)
    @Column(name = "nome_atendido", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadocivil_atendido", nullable = false)
    private EstadoCivil estadoCivil;

    @Column(name = "profissao_atendido", nullable = false)
    private String profissao;

    @Column(name = "data_nasc_atendido", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "naturalidade_atendido", nullable = false)
    private String naturalidade;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(name = "cpf_atendido", nullable = false)
    private String cpf;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(name = "rg_atendido", nullable = false)
    private String rg;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(name = "celular_atendido", nullable = false)
    private String celular;

    @Convert(converter = FiliacaoConverter.class)
    @Column(name = "filiacao_atendido", nullable = false)
    private List<String> filiacao;

    @Column(nullable = false)
    private String observacoes;

    @Convert(converter = EnderecoStringConverter.class)
    private Endereco endereco;

    @ColumnDefault(value = "1")
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "fk_atividade_id", referencedColumnName = "atividade_id")
    private Atividade atividade;

    public static Atendimento detalhesToEntity(DetalhesAtendimento dto, Atividade atividade){
        return Atendimento.builder()
            .ativo(true)
            .celular(dto.getCelular())
            .cpf(dto.getCpf())
            .dataNascimento(dto.getDataNascimento())
            .endereco(dto.getEndereco())
            .estadoCivil(dto.getEstadoCivil())
            .filiacao(dto.getFiliacao())
            .naturalidade(dto.getNaturalidade())
            .nome(dto.getNome())
            .observacoes(dto.getObservacoes())
            .profissao(dto.getProfissao())
            .rg(dto.getRg())
            .atividade(atividade)
            .build();
    }

}
