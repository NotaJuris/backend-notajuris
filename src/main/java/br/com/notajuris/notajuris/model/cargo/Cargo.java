package br.com.notajuris.notajuris.model.cargo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cargo")
public class Cargo{

    public Cargo(CargoNome nome){
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_id", nullable = false)
    private Integer id;

    @Column(name = "nome_cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoNome nome;

    private Boolean ativo = true;
    
}
