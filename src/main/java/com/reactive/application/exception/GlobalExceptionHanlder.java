package com.reactive.application.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHanlder {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> BadReourceRequestException(WebExchangeBindException e) {
        Map<String, String> errorsMap = new HashMap<>();
        
        e
        .getBindingResult()
        .getFieldErrors()
        .forEach(error -> errorsMap.put(error.getField(), error.getDefaultMessage()));
        System.out.println("entro");
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            "Unvalid request body",
            errorsMap,
            "hola");
        
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontrado.class)
    public ResponseEntity<ErrorResponse> manejadorRecursoNoEncontrado(RecursoNoEncontrado e){
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            "Recurso con el id suministrado no encontrado en la base de datos"
            , "hola");
        
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);

    }
}
