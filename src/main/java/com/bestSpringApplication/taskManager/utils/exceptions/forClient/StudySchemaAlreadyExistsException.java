package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class StudySchemaAlreadyExistsException extends ExceptionForRestControllerAdvice{

    public StudySchemaAlreadyExistsException(String message) {
        super(message);
    }
}
