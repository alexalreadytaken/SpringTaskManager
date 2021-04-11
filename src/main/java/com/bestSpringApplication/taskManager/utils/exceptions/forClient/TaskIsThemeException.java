package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskIsThemeException extends BadRequestException {
    public TaskIsThemeException(String message) {
        super(message);
    }
}
