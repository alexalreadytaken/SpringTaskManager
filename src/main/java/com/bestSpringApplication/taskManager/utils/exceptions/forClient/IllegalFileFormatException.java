package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class IllegalFileFormatException extends ExceptionForRestControllerAdvice {

    public IllegalFileFormatException(String message) {
        super(message);
    }
}
