package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class IllegalFileFormatException extends ClientException{

    public IllegalFileFormatException(String message) {
        super(message);
    }
}
