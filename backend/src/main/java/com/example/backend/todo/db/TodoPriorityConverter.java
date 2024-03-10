package com.example.backend.todo.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TodoPriorityConverter implements AttributeConverter<TodoPriority, Character> {
    @Override
    public Character convertToDatabaseColumn(TodoPriority todoPriority) {
        return todoPriority != null ? todoPriority.getCode() : null;
    }

    @Override
    public TodoPriority convertToEntityAttribute(Character dbCode) {
        if (dbCode == null) {
            return null;
        }

        return Stream.of(TodoPriority.values())
                .filter(tp -> tp.getCode() == dbCode)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
