package com.bestSpringApplication.taskManager.handlers.exceptions;

public class EmailExistsException extends ClientException{


    public EmailExistsException(String message) {
        super(message);
    }
}
