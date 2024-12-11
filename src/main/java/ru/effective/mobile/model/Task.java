package ru.effective.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.model.enums.TaskPriority;
import ru.effective.mobile.model.enums.TaskState;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;
    private TaskPriority priority;
    private TaskState state;
    private LocalDateTime created;
}
