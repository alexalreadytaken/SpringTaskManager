package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ServerException extends ExceptionForRestControllerAdvice {

    public ServerException(String message) {
        super(message);
    }
}
