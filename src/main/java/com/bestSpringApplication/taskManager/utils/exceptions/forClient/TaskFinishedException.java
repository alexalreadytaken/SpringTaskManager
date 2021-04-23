package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskFinishedException extends BadRequestException{
    public TaskFinishedException(String message) {
        super(message);
    }
}
