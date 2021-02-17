package com.bestSpringApplication.taskManager.handlers.exceptions;

public class IllegalFileFormatException extends ClientException{
    public IllegalFileFormatException(String path, String message) {
        super(path, message);
    }
}
