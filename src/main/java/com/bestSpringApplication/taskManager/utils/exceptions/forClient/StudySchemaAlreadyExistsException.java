package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

public class StudySchemaAlreadyExistsException extends BadRequestException{

    public StudySchemaAlreadyExistsException(String message) {
        super(message);
    }
}
