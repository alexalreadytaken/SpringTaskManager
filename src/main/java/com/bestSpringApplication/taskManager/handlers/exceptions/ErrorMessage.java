package com.bestSpringApplication.taskManager.handlers.exceptions;

public class ErrorMessage {
    private int status;
    private long timestamp;
    private String result;

    public ErrorMessage(int status, long timestamp, String result) {
        this.status = status;
        this.timestamp = timestamp;
        this.result = result;
    }

    public ErrorMessage() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
