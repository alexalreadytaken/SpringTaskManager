package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class BadRequestException extends ExceptionForRestControllerAdvice {

    public BadRequestException(String message) {
        super(message);
    }
}
