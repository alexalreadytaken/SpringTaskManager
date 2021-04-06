package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.utils.exceptions.forClient.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

// TODO: 3/24/2021 split
@RestControllerAdvice
public class RestExceptionsHandler{

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ErrorMessage contentNotFound(HttpServletRequest request, ContentNotFoundException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage taskIsTheme(HttpServletRequest request, TaskIsThemeException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage taskClosed(HttpServletRequest request, TaskClosedException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage taskInWork(HttpServletRequest request, TaskInWorkException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage userNotFound(HttpServletRequest request,UserNotFoundException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static ErrorMessage illegalFile(HttpServletRequest request,IllegalFileFormatException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage illegalXml(HttpServletRequest request,IllegalXmlFormatException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ErrorMessage emailExists(HttpServletRequest request,EmailExistsException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ErrorMessage unknownServerEx(HttpServletRequest request,ServerException ex){
        return ErrorMessageFactory.newErrorMsg(ex,request);
    }
}
