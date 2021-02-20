package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ClientException extends RestControllerAdviceScopeException{

    public ClientException(String message) {
        super(message);
    }
}
