package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ServerException extends RestControllerAdviceScopeException{

    public ServerException(String message) {
        super(message);
    }
}
