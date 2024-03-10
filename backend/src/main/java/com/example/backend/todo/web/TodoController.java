package com.example.backend.todo.web;

import com.example.backend.todo.app.TodoServiceImpl;
import com.example.backend.todo.app.dto.TodoSpec;
import com.example.backend.todo.db.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoServiceImpl service;

    private final TodoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<?> getAll() {

        var todoModels = service.getAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {

        var todoModel = assembler.toModel(service.get(id));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoModel);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody TodoSpec spec) {

        var todoModel = assembler.toModel(service.add(spec));

        return ResponseEntity
                .created(todoModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,
                                       @RequestBody TodoSpec spec) {

        var todoModel = assembler.toModel(service.update(id, spec));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
