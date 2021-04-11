package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.utils.exceptions.forClient.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionsHandler{

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ErrorMessage contentNotFound(HttpServletRequest request, ContentNotFoundException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage taskIsTheme(HttpServletRequest request, BadRequestException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static ErrorMessage illegalFile(HttpServletRequest request,IllegalFileFormatException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ErrorMessage unknownServerEx(HttpServletRequest request,ServerException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }
}
