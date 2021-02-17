package com.bestSpringApplication.taskManager.handlers.exceptions;

public class UserNotFoundException extends ClientException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
