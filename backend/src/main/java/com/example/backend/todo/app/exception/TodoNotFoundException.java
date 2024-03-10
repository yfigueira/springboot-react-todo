package com.example.backend.todo.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TodoNotFoundException extends RuntimeException{

    public TodoNotFoundException(String message) {
        super(message);
    }
}
