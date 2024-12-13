package ru.effective.mobile.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на добавление нового комментария")
public class NewCommentDto {
    @Schema(description = "Текст комментария", example = "Text comment.")
    @Size(min = 5, max = 255, message = "Текст комментария должно содержать от 5 до 255 символов")
    @NotBlank(message = "Текст комментария не может быть пустыми")
    private String description;
}
