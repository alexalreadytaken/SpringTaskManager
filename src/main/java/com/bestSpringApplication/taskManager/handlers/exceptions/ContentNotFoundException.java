package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ContentNotFoundException extends ClientException{
    public ContentNotFoundException(String path, String message) {
        super(path, message);
    }
}
