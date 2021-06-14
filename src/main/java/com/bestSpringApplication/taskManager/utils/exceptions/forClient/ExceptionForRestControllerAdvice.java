package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class ExceptionForRestControllerAdvice extends RuntimeException{

    public ExceptionForRestControllerAdvice(String message) {
        super(message);
    }
}
