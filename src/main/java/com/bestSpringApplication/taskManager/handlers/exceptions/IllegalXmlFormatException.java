package com.bestSpringApplication.taskManager.handlers.exceptions;

public class IllegalXmlFormatException extends ClientException{

    public IllegalXmlFormatException(String path, String message) {
        super(path, message);
    }
}
