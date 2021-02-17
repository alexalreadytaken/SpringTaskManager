package com.bestSpringApplication.taskManager.handlers.exceptions;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ErrorMessageFactory {

    public static <T extends RestControllerAdviceScopeException> ErrorMessage newErrorMsg(T ex, HttpServletRequest request){
        return new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }

}
