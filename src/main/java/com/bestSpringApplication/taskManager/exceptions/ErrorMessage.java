package com.bestSpringApplication.taskManager.exceptions;

public class ErrorMessage {
    private int status;
    private String message;

    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorMessage() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
