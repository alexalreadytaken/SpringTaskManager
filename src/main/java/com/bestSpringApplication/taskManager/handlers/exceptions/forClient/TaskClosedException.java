package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class TaskClosedException extends ClientException{
    public TaskClosedException(String message) {
        super(message);
    }
}
