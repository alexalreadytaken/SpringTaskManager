package com.bestSpringApplication.taskManager.handlers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

// FIXME: 2/17/2021 come up with a normal name
@AllArgsConstructor
@Getter
public class RestControllerAdviceScopeException extends RuntimeException{
    private final String path;
    private final String message;
}
