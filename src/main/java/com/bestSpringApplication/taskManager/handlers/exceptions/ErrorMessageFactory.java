package com.bestSpringApplication.taskManager.handlers.exceptions;

import java.time.Instant;
import java.time.LocalDateTime;

public class ErrorMessageFactory {

    public static <T extends ClientException> ErrorMessage newClientErrorMsg(T ex){
        return new ErrorMessage(
            LocalDateTime.now(),
            ex.getMessage()
        );
    }

}
