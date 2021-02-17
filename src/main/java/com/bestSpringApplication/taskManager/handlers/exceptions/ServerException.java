package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ServerException extends RestControllerAdviceScopeException{
    public ServerException(String path, String message) {
        super(path, message);
    }
}
