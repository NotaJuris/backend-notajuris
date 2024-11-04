package br.com.notajuris.notajuris.infra.bean;

import org.springframework.stereotype.Component;

import br.com.notajuris.notajuris.model.Endereco;
import jakarta.persistence.AttributeConverter;

@Component
public class EnderecoStringConverter implements AttributeConverter<Endereco, String>{

    @Override
    public String convertToDatabaseColumn(Endereco attribute) {
        return attribute.toString();
    }

    @Override
    public Endereco convertToEntityAttribute(String dbData) {
        //preparando string
        String[] campos = dbData.split("[,-]");

        for(int i = 0; i<campos.length; i++){
            campos[i] = campos[i].trim();
        }
    //transforma string em endereco
        Endereco endereco = null;
        if(campos.length < 6){
            endereco = new Endereco(campos[0], campos[1], campos[2], campos[3], campos[4], null);
        } else {
            endereco = new Endereco(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]);
        }
        return endereco;
    }
    
}
