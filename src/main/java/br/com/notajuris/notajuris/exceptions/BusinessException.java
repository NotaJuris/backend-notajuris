package br.com.notajuris.notajuris.exceptions;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private HttpStatusCode statusCode;

    public BusinessException(String message, HttpStatusCode statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}