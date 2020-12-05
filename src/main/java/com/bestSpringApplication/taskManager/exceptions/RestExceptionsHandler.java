package com.bestSpringApplication.taskManager.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionsHandler{

    @ExceptionHandler
    public static ResponseEntity<Object> userNotFoundException(ContentNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage message = new ErrorMessage(notFound.value(),ex.getMessage());
        return new ResponseEntity<>(message,notFound);
    }
}
