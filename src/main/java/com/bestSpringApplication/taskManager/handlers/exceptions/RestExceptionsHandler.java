package com.bestSpringApplication.taskManager.handlers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionsHandler{

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ErrorMessage notFound(ContentNotFoundException ex){
        return ErrorMessageFactory.newClientErrorMsg(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static ErrorMessage illegalFile(IllegalFileFormatException ex){
        return ErrorMessageFactory.newClientErrorMsg(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage illegalXml(IllegalXmlFormatException ex){
        return ErrorMessageFactory.newClientErrorMsg(ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage emailExists(EmailExistsException ex){
        return ErrorMessageFactory.newClientErrorMsg(ex);
    }

}
