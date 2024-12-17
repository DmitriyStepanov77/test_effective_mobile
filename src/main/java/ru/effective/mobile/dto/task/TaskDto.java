package ru.effective.mobile.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.dto.comment.CommentDto;
import ru.effective.mobile.model.enums.TaskPriority;
import ru.effective.mobile.model.enums.TaskState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String performer;
    private TaskPriority priority;
    private TaskState state;
    private LocalDateTime created;
    private List<CommentDto> comments;
}
