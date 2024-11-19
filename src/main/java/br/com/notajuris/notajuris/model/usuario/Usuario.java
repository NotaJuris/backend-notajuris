package br.com.notajuris.notajuris.model.usuario;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.notajuris.notajuris.model.cargo.Cargo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.notajuris.notajuris.infra.bean.AesEncryptionConfig;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(nullable = false)
    private String nome;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(nullable = false)
    private String telefone;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(nullable = false)
    private String email;

    private Integer periodo;

    @Convert(converter = AesEncryptionConfig.class)
    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @ColumnDefault(value = "1")
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TurnoAluno turno;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cargo_id", referencedColumnName = "cargo_id")
    private Cargo cargo;

    private String semestre;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch(cargo.getNome()){
            case SUPERADMIN:
                return List.of(
                    new SimpleGrantedAuthority("ROLE_SUPERADMIN"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_ALUNO")
                );
            case ADMIN:
                return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_ALUNO")
                );
            default:
                return List.of(
                    new SimpleGrantedAuthority("ROLE_ALUNO")
                );
        }
    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
