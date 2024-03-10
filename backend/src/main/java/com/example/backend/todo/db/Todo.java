package com.example.backend.todo.db;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task")
    private String task;

    @Convert(converter = TodoPriorityConverter.class)
    @Column(name = "priority")
    private TodoPriority priority;

    @Column(name = "is_completed")
    private boolean isCompleted;
}
