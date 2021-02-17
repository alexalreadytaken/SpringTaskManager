package com.bestSpringApplication.taskManager.handlers.exceptions;

public class EmailExistsException extends ClientException{

    public EmailExistsException(String path, String message) {
        super(path, message);
    }
}
