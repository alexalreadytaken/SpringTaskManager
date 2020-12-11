package com.bestSpringApplication.taskManager.handlers.exceptions;

public class IllegalFileFormatException extends RuntimeException{
    public IllegalFileFormatException(String message) {
        super(message);
    }
}
