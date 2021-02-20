package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class ContentNotFoundException extends ClientException{

    public ContentNotFoundException(String message) {
        super(message);
    }
}
