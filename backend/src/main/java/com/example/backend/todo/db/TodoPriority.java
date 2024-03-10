package com.example.backend.todo.db;

public enum TodoPriority {
    HIGH('H'),
    MEDIUM('M'),
    LOW('L');

    private final char code;

    TodoPriority(char code) {
        this.code = code;
    }

    char getCode() {
        return this.code;
    }
}
