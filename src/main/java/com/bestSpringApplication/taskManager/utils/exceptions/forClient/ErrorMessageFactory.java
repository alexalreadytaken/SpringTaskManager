package com.bestSpringApplication.taskManager.utils.exceptions.forClient;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
public class ErrorMessageFactory {

    public static <T extends ExceptionForRestControllerAdvice> ErrorMessage newErrorMsg(T ex, HttpServletRequest request) {
        ErrorMessage errorMessage = getErrorMessage(request, ex.getMessage());
        log.trace("Return exception '{}' to client, message = '{}'",ex,errorMessage);
        return errorMessage;
    }

    public static ErrorMessage getErrorMessage(HttpServletRequest request,String exMessage) {
        String requestURI = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
        return new ErrorMessage(
                LocalDateTime.now(),
                exMessage, requestURI,
                request.getQueryString());
    }
}
