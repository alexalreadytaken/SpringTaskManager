package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class ContentNotFoundException extends ClientException{

    public ContentNotFoundException(String message) {
        super(message);
    }
}
