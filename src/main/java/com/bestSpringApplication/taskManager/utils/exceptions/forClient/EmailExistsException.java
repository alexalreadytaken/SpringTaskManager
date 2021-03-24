package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class EmailExistsException extends ClientException{


    public EmailExistsException(String message) {
        super(message);
    }
}
