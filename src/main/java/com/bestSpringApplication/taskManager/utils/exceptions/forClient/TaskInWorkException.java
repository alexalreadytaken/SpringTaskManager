package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskInWorkException extends BadRequestException {
    public TaskInWorkException(String message) {
        super(message);
    }
}
