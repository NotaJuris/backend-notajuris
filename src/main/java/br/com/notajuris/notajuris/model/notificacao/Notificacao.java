package br.com.notajuris.notajuris.model.notificacao;

import org.hibernate.annotations.ColumnDefault;

import br.com.notajuris.notajuris.model.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificacao")
@Entity
public class Notificacao {

    public Notificacao(String titulo, Usuario destinatario, String mensagem){
        this.titulo = titulo;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacao_id")
    private Integer id;

    private String titulo;

    private String mensagem;

    @ColumnDefault(value = "true")
    Boolean visto = true;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id", referencedColumnName = "usuario_id")
    Usuario destinatario;
}
