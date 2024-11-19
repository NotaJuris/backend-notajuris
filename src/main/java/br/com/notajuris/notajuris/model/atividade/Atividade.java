package br.com.notajuris.notajuris.model.atividade;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.ColumnDefault;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "atividade")
public class Atividade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_id")
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAtividade tipo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusAtividade status;

    @Column(name="data_atividade", nullable=false)
    private LocalDate dataAtividade;

    @Column(name="hora_atividade")
    private LocalTime horaAtividade;

    @Column(name="carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    @Transient
    DetalhesAtividade detalhes;

    private String semestre;

    @ColumnDefault(value = "1")
    private Boolean ativo;

    public static Atividade toEntity(AtividadeDto dto, Usuario usuario){
        return Atividade.builder()
                .tipo(dto.tipo())
                .descricao(dto.descricao())
                .cargaHoraria(dto.carga_horaria())
                .dataAtividade(dto.data_atividade())
                .horaAtividade(dto.hora_atividade())
                .usuario(usuario)
                .status(StatusAtividade.PENDENTE)
                .detalhes(dto.detalhes())
                .ativo(true)
                .build();
    }

    
}
