package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskClosedException extends BadRequestException {
    public TaskClosedException(String message) {
        super(message);
    }
}
