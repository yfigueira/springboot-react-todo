package com.example.backend.todo.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyTaskException extends IllegalArgumentException {

    public EmptyTaskException(String message) {
        super(message);
    }
}
