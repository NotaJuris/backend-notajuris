package br.com.notajuris.notajuris.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }
}