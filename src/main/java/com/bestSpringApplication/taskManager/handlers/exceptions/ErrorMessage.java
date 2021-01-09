package com.bestSpringApplication.taskManager.handlers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorMessage {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime whenHappen;
    private String result;

    public ErrorMessage(LocalDateTime whenHappen, String result) {
        this.whenHappen = whenHappen;
        this.result = result;
    }

    public ErrorMessage() {
    }

    public LocalDateTime getWhenHappen() {
        return whenHappen;
    }

    public void setWhenHappen(LocalDateTime whenHappen) {
        this.whenHappen = whenHappen;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
