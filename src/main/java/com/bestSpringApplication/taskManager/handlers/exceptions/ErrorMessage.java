package com.bestSpringApplication.taskManager.handlers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorMessage {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String result;

    public ErrorMessage(LocalDateTime time, String result) {
        this.time = time;
        this.result = result;
    }

    public ErrorMessage() {}

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
