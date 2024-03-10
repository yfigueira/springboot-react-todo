package com.example.backend.todo.web;

import com.example.backend.todo.db.Todo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TodoModelAssembler implements RepresentationModelAssembler<Todo, EntityModel<Todo>> {

    @Override
    public EntityModel<Todo> toModel(Todo todo) {

        return EntityModel.of(todo,
                linkTo(methodOn(TodoController.class).get(todo.getId())).withSelfRel(),
                linkTo(methodOn(TodoController.class).getAll()).withRel("todos"));
    }
}
