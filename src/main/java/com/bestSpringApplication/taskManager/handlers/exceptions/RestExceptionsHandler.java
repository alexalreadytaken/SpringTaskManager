package com.bestSpringApplication.taskManager.handlers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionsHandler{





    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ErrorMessage notFoundHandle(ContentNotFoundException ex){
        return new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Instant.now().toEpochMilli(),
            ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static ErrorMessage illegalFileHandle(IllegalFileFormatException ex){
        return new ErrorMessage(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
            Instant.now().toEpochMilli(),
            ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage illegalXmlHandle(IllegalXmlFormatException ex){
        return new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toEpochMilli(),
            ex.getMessage());
    }
}
