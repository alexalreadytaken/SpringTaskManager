package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
public class ErrorMessageFactory {

    public static <T extends ExceptionForRestControllerAdvice> ErrorMessage newErrorMsg(T ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getRequestURI()
        );
        log.warn("Return exception '{}' to client",errorMessage);
        return errorMessage;
    }

}
