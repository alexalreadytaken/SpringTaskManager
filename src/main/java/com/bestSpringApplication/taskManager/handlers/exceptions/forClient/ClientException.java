package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ClientException extends ExceptionForRestControllerAdvice {

    public ClientException(String message) {
        super(message);
    }
}
