package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class IllegalXmlFormatException extends BadRequestException {

    public IllegalXmlFormatException(String message) {
        super(message);
    }
}
