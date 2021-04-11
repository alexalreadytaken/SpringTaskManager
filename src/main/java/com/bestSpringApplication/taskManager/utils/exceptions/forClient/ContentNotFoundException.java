package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class ContentNotFoundException extends ExceptionForRestControllerAdvice {

    public ContentNotFoundException(String message) {
        super(message);
    }
}
