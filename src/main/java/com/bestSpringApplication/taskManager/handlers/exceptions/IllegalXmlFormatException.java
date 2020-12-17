package com.bestSpringApplication.taskManager.handlers.exceptions;

public class IllegalXmlFormatException extends RuntimeException{
    public IllegalXmlFormatException(String message) {
        super(message);
    }
}
