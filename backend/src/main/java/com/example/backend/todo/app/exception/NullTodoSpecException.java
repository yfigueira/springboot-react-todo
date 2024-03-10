package com.example.backend.todo.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NullTodoSpecException extends RuntimeException {

    public NullTodoSpecException(String message) {
        super(message);
    }
}
