package com.bestSpringApplication.taskManager.exceptions;

public class IllegalFileFormatException extends RuntimeException{
    public IllegalFileFormatException(String message) {
        super(message);
    }
}
