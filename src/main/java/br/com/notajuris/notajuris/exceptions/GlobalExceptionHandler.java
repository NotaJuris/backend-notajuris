package br.com.notajuris.notajuris.exceptions;

import javax.naming.AuthenticationException;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handler(Exception ex){

        ProblemDetail errorDetail = null;

        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            errorDetail = ProblemDetail.forStatusAndDetail(businessException.getStatusCode(), businessException.getMessage());
        }

        return errorDetail;
    }
    
}
