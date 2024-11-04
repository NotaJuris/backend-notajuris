package br.com.notajuris.notajuris.infra.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;

@Component
public class FiliacaoConverter implements AttributeConverter<List<String>, String>{

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // pega a lista, transforma em string
        return String.join(", ", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // TODO Auto-generated method stub
        return List.of(dbData.split(", "));
    }

    
}
