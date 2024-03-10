package com.example.backend.todo.app.dto;

import com.example.backend.todo.db.TodoPriority;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoSpec {

    private String task;
    private TodoPriority priority;
    private boolean isCompleted;
}
