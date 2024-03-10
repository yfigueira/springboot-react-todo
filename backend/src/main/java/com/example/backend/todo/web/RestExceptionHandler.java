package com.example.backend.todo.web;

import com.example.backend.todo.app.exception.EmptyTaskException;
import com.example.backend.todo.app.exception.NullTodoSpecException;
import com.example.backend.todo.app.exception.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(TodoNotFoundException exception) {
        return handle(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyTaskException.class)
    public ResponseEntity<ErrorResponse> handleEmptyTask(EmptyTaskException exception) {
        return handle(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullTodoSpecException.class)
    public ResponseEntity<ErrorResponse> handleNullTodoSpec(NullTodoSpecException exception) {
        return handle(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
        return handle(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handle(String message, HttpStatus status) {
        var errorResponse = ErrorResponse.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        log.error("%s [ %s ]: %s".formatted(errorResponse.getTimestamp(), errorResponse.getStatus(), errorResponse.getMessage()));

        return new ResponseEntity<>(errorResponse, status);
    }
}
