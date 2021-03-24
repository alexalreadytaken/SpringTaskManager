package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskClosedException extends ClientException{
    public TaskClosedException(String message) {
        super(message);
    }
}
