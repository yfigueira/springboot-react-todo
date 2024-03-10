package com.example.backend.todo.app;

import com.example.backend.todo.app.exception.EmptyTaskException;
import com.example.backend.todo.app.exception.NullTodoSpecException;
import com.example.backend.todo.app.exception.TodoNotFoundException;
import com.example.backend.todo.db.Todo;
import com.example.backend.todo.db.TodoRepository;
import com.example.backend.todo.app.dto.TodoSpec;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{


    private final TodoRepository repository;

    @Override
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @Override
    public Todo get(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Todo add(TodoSpec todoSpec) {
        if (todoSpec == null) throw new NullTodoSpecException("Create failure - Todo spec was null");
        if (todoSpec.getTask().isEmpty()) throw new EmptyTaskException("Create failure - Empty task not allowed");

        Todo newTodo = Todo.builder()
                .task(todoSpec.getTask())
                .priority(todoSpec.getPriority())
                .isCompleted(todoSpec.isCompleted())
                .build();

        return repository.save(newTodo);
    }

    @Override
    public Todo update(long todoId, TodoSpec specForUpdate) {
        if (specForUpdate == null) throw new NullTodoSpecException("Update failure - Todo spec was null");
        if (specForUpdate.getTask().isEmpty()) throw new EmptyTaskException("Update failure - Empty task not allowed");

        Todo todoForUpdate = repository
                .findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Update failure - Todo with id %s was not found".formatted(todoId)));

        todoForUpdate.setTask(specForUpdate.getTask());
        todoForUpdate.setPriority(specForUpdate.getPriority());
        todoForUpdate.setCompleted(specForUpdate.isCompleted());

        return repository.save(todoForUpdate);
    }

    @Override
    public void delete(long todoId) {

        Todo todoForDelete = repository
                .findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Delete failure - Todo with id %s was not found".formatted(todoId)));

        repository.delete(todoForDelete);
    }
}
