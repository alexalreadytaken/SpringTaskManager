package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

// FIXME: 2/17/2021 come up with a normal name
public class RestControllerAdviceScopeException extends RuntimeException{
    public RestControllerAdviceScopeException(String message) {
        super(message);
    }
}
