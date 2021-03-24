package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class ServerException extends ExceptionForRestControllerAdvice {

    public ServerException(String message) {
        super(message);
    }
}
