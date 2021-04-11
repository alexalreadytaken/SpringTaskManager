package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
