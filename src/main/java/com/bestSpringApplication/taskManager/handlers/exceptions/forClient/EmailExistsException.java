package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class EmailExistsException extends ClientException{


    public EmailExistsException(String message) {
        super(message);
    }
}
