package com.example.backend.todo.web;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;
}
