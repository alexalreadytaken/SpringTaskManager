package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
public class ErrorMessageFactory {

    public static <T extends ExceptionForRestControllerAdvice> ErrorMessage newErrorMsg(T ex, HttpServletRequest request) {
        String requestURI = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),ex.getMessage(),requestURI,request.getQueryString());
        log.trace("Return exception '{}' to client, message = '{}'",ex,errorMessage);
        return errorMessage;
    }

}
