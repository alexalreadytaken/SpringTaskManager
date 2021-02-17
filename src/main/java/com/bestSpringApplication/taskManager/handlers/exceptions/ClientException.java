package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ClientException extends RestControllerAdviceScopeException{

    public ClientException(String message) {
        super(message);
    }
}
