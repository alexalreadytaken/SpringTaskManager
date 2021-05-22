package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class InvalidRequestParamException extends BadRequestException{
    public InvalidRequestParamException(String message) {
        super(message);
    }
}
