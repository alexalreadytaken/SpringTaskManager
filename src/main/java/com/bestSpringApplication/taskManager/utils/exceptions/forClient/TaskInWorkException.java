package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class TaskInWorkException extends ClientException{
    public TaskInWorkException(String message) {
        super(message);
    }
}
