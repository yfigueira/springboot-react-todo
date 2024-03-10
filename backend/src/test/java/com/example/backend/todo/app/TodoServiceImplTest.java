package com.example.backend.todo.app;

import com.example.backend.todo.app.exception.EmptyTaskException;
import com.example.backend.todo.app.exception.NullTodoSpecException;
import com.example.backend.todo.app.exception.TodoNotFoundException;
import com.example.backend.todo.db.Todo;
import com.example.backend.todo.db.TodoPriority;
import com.example.backend.todo.db.TodoRepository;
import com.example.backend.todo.app.dto.TodoSpec;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @InjectMocks
    TodoServiceImpl SUT;

    @Mock
    TodoRepository mockRepository;

    @Test
    void getAll_whenRepoHasTodos_shouldReturnAllTodos() {
        when(mockRepository.findAll()).thenReturn(List.of(
                new Todo(1L, "first todo", TodoPriority.LOW, false),
                new Todo(2L, "second todo", TodoPriority.LOW, false)
        ));

        assertThat(SUT.getAll(), hasSize(2));
    }

    @Test
    void getAll_whenRepoIsEmpty_shouldReturnEmptyList() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(SUT.getAll(), is(Collections.emptyList()));
    }

    @Test
    void get_whenTodoExists_shouldReturnTodoWithGivenId() {
        Todo mockTodo = new Todo(1L, "Found todo", TodoPriority.LOW, false);
        when(mockRepository.findById(1L)).thenReturn(Optional.of(mockTodo));

        assertThat(SUT.get(1L), is(mockTodo));
    }

    @Test
    void get_whenTodoDoesNotExist_shouldReturnNull() {
        when(mockRepository.findById(404L)).thenReturn(Optional.empty());

        assertThat(SUT.get(404L), nullValue());
    }

    @Test
    void add_whenTodoIsValid_shouldReturnSavedTodo() {
        TodoSpec spec = new TodoSpec(
                "Valid Spec", TodoPriority.LOW, false
        );

        Todo validTodo = Todo.builder()
                        .task(spec.getTask())
                        .priority(spec.getPriority())
                        .isCompleted(spec.isCompleted())
                        .build();

        Todo newTodo = Todo.builder()
                        .id(1L)
                        .task(spec.getTask())
                        .priority(spec.getPriority())
                        .isCompleted(spec.isCompleted())
                        .build();


        when(mockRepository.save(validTodo)).thenReturn(newTodo);

        assertThat(SUT.add(spec), is(newTodo));
    }

    @Test
    void add_whenTodoTaskIsEmpty_shouldThrowEmptyTaskException() {
        TodoSpec emptyTaskSpec = new TodoSpec(
                "", TodoPriority.LOW, false
        );

        assertThrows(EmptyTaskException.class, () -> SUT.add(emptyTaskSpec));
    }

    @Test
    void add_whenThrowingEmptyTaskException_shouldHaveMessageAboutEmptyTask() {
        TodoSpec emptyTaskSpec = new TodoSpec(
                "", TodoPriority.LOW, false
        );

        String expectedExceptionMessage = "Create failure - Empty task not allowed";
        Throwable exception = assertThrows(EmptyTaskException.class, () -> SUT.add(emptyTaskSpec));

        assertThat(exception.getMessage(), is(expectedExceptionMessage));
    }

    @Test
    void add_whenSpecIsNull_shouldThrowNullTodoSpecException() {
        assertThrows(NullTodoSpecException.class, () -> SUT.add(null));
    }

    @Test
    void add_whenThrowingNullTodoSpecException_shouldHaveMessageAboutNullSpec() {
        String expectedMessage = "Create failure - Todo spec was null";
        Throwable exception = assertThrows(NullTodoSpecException.class, () -> SUT.add(null));

        assertThat(exception.getMessage(), is(expectedMessage));
    }

    @Test
    void update_whenTodoExists_shouldReturnTodoWithAppliedChanges() {
        TodoSpec specForUpdate = new TodoSpec(
                "Updated task", TodoPriority.HIGH, false
        );

        final long todoToUpateId = 1L;

        Todo originalTodo = Todo.builder()
                            .id(todoToUpateId)
                            .task("Original task")
                            .priority(TodoPriority.HIGH)
                            .isCompleted(false)
                            .build();

        Todo updatedTodo = Todo.builder()
                            .id(todoToUpateId)
                            .task(specForUpdate.getTask())
                            .priority(specForUpdate.getPriority())
                            .isCompleted(specForUpdate.isCompleted())
                            .build();

        when(mockRepository.findById(todoToUpateId)).thenReturn(Optional.of(originalTodo));
        when(mockRepository.save(updatedTodo)).thenReturn(updatedTodo);

        assertThat(SUT.update(todoToUpateId, specForUpdate), is(updatedTodo));
    }

    @Test
    void update_whenTodoDoesNotExist_shouldThrowTodoNotFoundException() {
        final long invalidId = -1L;

        TodoSpec spec = new TodoSpec(
                "Task", TodoPriority.LOW, false
        );

        assertThrows(TodoNotFoundException.class, () -> SUT.update(invalidId, spec));
    }

    @Test
    void update_whenThrowingTodoNotFoundExceptionForWrongId_shouldHaveMessageAboutWrongId() {
        final long invalidId = -111L;

        TodoSpec spec = new TodoSpec(
                "Task", TodoPriority.LOW, false
        );

        String expectedExceptionMessage = "Update failure - Todo with id %s was not found".formatted(invalidId);
        Throwable exception = assertThrows(TodoNotFoundException.class, () -> SUT.update(invalidId, spec));

        assertThat(exception.getMessage(), is(expectedExceptionMessage));
    }

    @Test
    void update_whenSpecHasEmptyTask_shouldThrowEmptyTaskException() {
        TodoSpec emptyTaskSpec = new TodoSpec(
                "", TodoPriority.LOW, false
        );

        assertThrows(EmptyTaskException.class, () -> SUT.update(1L, emptyTaskSpec));
    }

    @Test
    void update_whenThrowingEmptyTaskException_shouldHaveMessageAboutEmptyTask() {
        TodoSpec emptyTaskSpec = new TodoSpec(
                "", TodoPriority.LOW, false
        );

        String expectedExceptionMessage = "Update failure - Empty task not allowed";
        Throwable exception = assertThrows(EmptyTaskException.class, () -> SUT.update(1L, emptyTaskSpec));

        assertThat(exception.getMessage(), is(expectedExceptionMessage));
    }

    @Test
    void update_whenTodoSpecIsNull_shouldThrowNullTodoSpecException() {
        assertThrows(NullTodoSpecException.class, () -> SUT.update(1L, null));
    }

    @Test
    void update_whenThrowingNullTodoSpecException_shouldHaveMessageAboutNullTodoSpec() {
        String expectedMessage = "Update failure - Todo spec was null";
        Throwable exception = assertThrows(NullTodoSpecException.class, () -> SUT.update(1L, null));

        assertThat(exception.getMessage(), is(expectedMessage));
    }

    @Test
    void delete_whenTodoExists_shouldDeleteTodo() {
        Todo todoForDelete = Todo.builder()
                .id(1L)
                .task("Delete todo")
                .priority(TodoPriority.HIGH)
                .isCompleted(true)
                .build();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(todoForDelete));
        doNothing().when(mockRepository).delete(todoForDelete);

        SUT.delete(1L);

        verify(mockRepository, times(1)).delete(todoForDelete);
    }

    @Test
    void delete_whenTodoDoesNotExist_shouldThrowTodoNotFoundException() {
        final long invalidId = -1L;

        assertThrows(TodoNotFoundException.class, () -> SUT.delete(invalidId));
    }

    @Test
    void delete_whenThrowingTodoNotFoundExceptionForWrongId_shouldHaveMessageAboutWrongId() {
        final long invalidId = -111L;

        String expectedExceptionMessage = "Delete failure - Todo with id %s was not found".formatted(invalidId);
        Throwable exception = assertThrows(TodoNotFoundException.class, () -> SUT.delete(invalidId));

        assertThat(exception.getMessage(), is(expectedExceptionMessage));
    }
}