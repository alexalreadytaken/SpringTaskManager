package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class ClientException extends ExceptionForRestControllerAdvice {

    public ClientException(String message) {
        super(message);
    }
}
