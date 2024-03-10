package com.example.backend.todo.web;

import com.example.backend.todo.app.TodoService;
import com.example.backend.todo.app.dto.TodoSpec;
import com.example.backend.todo.db.Todo;
import com.example.backend.todo.db.TodoPriority;
import com.example.backend.todo.db.TodoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerIT {

    @LocalServerPort
    private Integer port;
    private final TodoService service;
    private final TodoRepository repository;
    private static final String BASE_PATH = "/api/todos";

    @Autowired
    public TodoControllerIT(TodoService service, TodoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldGetAllTodos() {
        service.add(new TodoSpec("Task nr 1", TodoPriority.HIGH, false));
        service.add(new TodoSpec("Task nr 2", TodoPriority.HIGH, false));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void shouldGetTodoById() {
        Todo todo = service.add(new TodoSpec("Task nr 1", TodoPriority.HIGH, false));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("%s/%s".formatted(BASE_PATH, todo.getId()))
                .then()
                .statusCode(200)
                .body("id", is(Math.toIntExact(todo.getId())))
                .body("task", is(todo.getTask()))
                .body("priority", is(todo.getPriority().name()))
                .body("completed", is(todo.isCompleted()));
    }

    @Test
    void shouldAddNewTodo() {
        String requestBody =
                """
                    {
                        "task": "New task",
                        "priority": "HIGH",
                        "completed": false
                    }
                """;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(201)
                .body("task", is("New task"))
                .body("priority", is("HIGH"))
                .body("completed", is(false));
    }

    @Test
    void add_whenTodoSpecHasEmptyTask_shouldReturnErrorResponseWithHttp400Status() {
        String requestBody =
                """
                    {
                        "task": "",
                        "priority": "HIGH",
                        "completed": false
                    }
                """;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(400)
                .body("message", is("Create failure - Empty task not allowed"));
    }

    @Test
    void shouldUpdateTodoById() {
        Todo todo = service.add(new TodoSpec("Old task", TodoPriority.LOW, false));

        assertThat(todo, notNullValue());
        assertThat(todo.getTask(), is("Old task"));
        assertThat(todo.getPriority(), is(TodoPriority.LOW));

        String requestBody =
                """
                    {
                        "task": "Updated task",
                        "priority": "MEDIUM",
                        "completed": false
                    }
                """;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("%s/%s".formatted(BASE_PATH, todo.getId()))
                .then()
                .statusCode(200)
                .body("id", is(Math.toIntExact(todo.getId())))
                .body("task", is("Updated task"))
                .body("priority", is("MEDIUM"))
                .body("completed", is(false));
    }

    @Test
    void update_whenTodoDoesNotExist_shouldReturnErrorResponseWithHttp404Status() {
        String requestBody =
                """
                    {
                        "task": "Updated Task",
                        "priority": "MEDIUM",
                        "completed": false
                    }
                """;

        long invalidId = -1L;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("%s/%s".formatted(BASE_PATH, invalidId))
                .then()
                .statusCode(404)
                .body("message", is("Update failure - Todo with id %s was not found".formatted(invalidId)));
    }

    @Test
    void update_whenTodoSpecHasEmptyTask_shouldReturnErrorResponseWithHttp400Status() {
        Todo todo = service.add(new TodoSpec("Old task", TodoPriority.LOW, false));

        assertThat(todo, notNullValue());
        assertThat(todo.getTask(), is("Old task"));
        assertThat(todo.getPriority(), is(TodoPriority.LOW));

        String requestBody =
                """
                    {
                        "task": "",
                        "priority": "MEDIUM",
                        "completed": false
                    }
                """;

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("%s/%s".formatted(BASE_PATH, todo.getId()))
                .then()
                .statusCode(400)
                .body("message", is("Update failure - Empty task not allowed"));
    }

    @Test
    void shouldDeleteTodoById() {
        Todo todo = service.add(new TodoSpec("Old task", TodoPriority.LOW, false));

        assertThat(todo, notNullValue());

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("%s/%s".formatted(BASE_PATH, todo.getId()))
                .then()
                .statusCode(204);
    }

    @Test
    void delete_whenTodoSDoesNotExist_shouldReturnErrorResponseWithHttp404Status() {
        long invalidId = -1L;

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("%s/%s".formatted(BASE_PATH, invalidId))
                .then()
                .statusCode(404)
                .body("message", is("Delete failure - Todo with id %s was not found".formatted(invalidId)));
    }
}