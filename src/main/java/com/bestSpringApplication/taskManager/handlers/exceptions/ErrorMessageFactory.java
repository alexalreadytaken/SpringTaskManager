package com.bestSpringApplication.taskManager.handlers.exceptions;

import java.time.LocalDateTime;

public class ErrorMessageFactory {

    public static <T extends RestControllerAdviceScopeException> ErrorMessage newErrorMsg(T ex){
        return new ErrorMessage(
            LocalDateTime.now(),
            ex.getMessage(),
            ex.getPath()
        );
    }

}
