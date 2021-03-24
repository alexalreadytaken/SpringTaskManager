package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class UserNotFoundException extends ClientException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
