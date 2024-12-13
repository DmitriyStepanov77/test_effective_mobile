package ru.effective.mobile.dto.comment;

import ru.effective.mobile.model.Task;
import java.time.LocalDateTime;

public class CommentDto {

    private Long id;
    private String author;
    private Task task;
    private String description;
    private LocalDateTime created;
}
