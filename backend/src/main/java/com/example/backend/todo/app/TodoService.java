package com.example.backend.todo.app;

import com.example.backend.todo.app.dto.TodoSpec;
import com.example.backend.todo.db.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getAll();

    Todo get(long id);

    Todo add(TodoSpec todoSpec);

    Todo update(long id, TodoSpec spec);

    void delete(long id);
}
