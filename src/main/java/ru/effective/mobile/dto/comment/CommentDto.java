package ru.effective.mobile.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String author;
    private String description;
    private LocalDateTime created;
}
