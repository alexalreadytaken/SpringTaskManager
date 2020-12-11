package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ContentNotFoundException extends RuntimeException{
    public ContentNotFoundException(String message) {
        super(message);
    }
}
