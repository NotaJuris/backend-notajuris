package br.com.notajuris.notajuris.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessExceptionDto> businessExceptionHandler(BusinessException e){
        return ResponseEntity.status(e.getStatusCode()).body(new BusinessExceptionDto(e.getMessage()));

    } 
    
}
