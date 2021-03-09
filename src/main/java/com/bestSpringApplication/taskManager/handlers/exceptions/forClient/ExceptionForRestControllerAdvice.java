package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ExceptionForRestControllerAdvice extends RuntimeException{
    public ExceptionForRestControllerAdvice(String message) {
        super(message);
    }
}
