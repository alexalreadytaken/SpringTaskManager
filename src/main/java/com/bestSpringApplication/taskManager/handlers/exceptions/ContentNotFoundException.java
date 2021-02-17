package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ContentNotFoundException extends ClientException{

    public ContentNotFoundException(String message) {
        super(message);
    }
}
