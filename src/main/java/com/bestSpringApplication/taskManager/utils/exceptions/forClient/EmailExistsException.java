package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class EmailExistsException extends BadRequestException {


    public EmailExistsException(String message) {
        super(message);
    }
}
