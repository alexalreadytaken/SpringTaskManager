package com.bestSpringApplication.taskManager.handlers.exceptions.forClient;

public class TaskInWorkException extends ClientException{
    public TaskInWorkException(String message) {
        super(message);
    }
}
