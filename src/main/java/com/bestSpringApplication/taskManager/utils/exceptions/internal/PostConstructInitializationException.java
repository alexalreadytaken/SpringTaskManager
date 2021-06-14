package com.bestSpringApplication.taskManager.utils.exceptions.internal;

public class PostConstructInitializationException extends RuntimeException{
    public PostConstructInitializationException(String message) {
        super(message);
    }

    public PostConstructInitializationException(Throwable cause) {
        super(cause);
    }

    public PostConstructInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
