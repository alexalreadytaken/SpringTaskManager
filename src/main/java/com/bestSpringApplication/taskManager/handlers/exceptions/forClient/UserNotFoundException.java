package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class UserNotFoundException extends ClientException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
