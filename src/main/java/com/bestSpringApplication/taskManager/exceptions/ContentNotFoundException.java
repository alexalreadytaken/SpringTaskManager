package com.bestSpringApplication.taskManager.exceptions;

public class ContentNotFoundException extends RuntimeException{
    public ContentNotFoundException(String message) {
        super(message);
    }
}
