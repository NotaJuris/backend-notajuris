package br.com.notajuris.notajuris.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class Endereco {

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String complemento;

    @Override
    public String toString() {
        if(this.complemento==null || this.complemento.isEmpty()){
            return String.format("%s, %s, %s, %s - %s", rua, bairro, numero, cidade, estado);
        }
        else {
            return String.format("%s, %s, %s, %s, %s - %s", rua, bairro, numero, complemento, cidade, estado);
        }
    }

    
}
