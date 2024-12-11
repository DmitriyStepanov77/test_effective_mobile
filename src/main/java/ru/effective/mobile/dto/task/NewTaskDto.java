package ru.effective.mobile.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.model.enums.TaskPriority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание новой задачи")
public class NewTaskDto {
    @Schema(description = "Заголовок задачи", example = "Big task")
    @Size(min = 5, max = 50, message = "Заголовок задачи должнен содержать от 5 до 50 символов")
    @NotBlank(message = "Заголовок задачи не может быть пустыми")
    private String title;
    @Schema(description = "Описание задачи", example = "To urgently complete a big task")
    @Size(min = 5, max = 255, message = "Описание задачи должно содержать от 5 до 255 символов")
    @NotBlank(message = "Описание задачи не может быть пустыми")
    private String description;
    @Schema(description = "Имя исполнителя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя исполнителя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя исполнителя не может быть пустыми")
    private String performer;
    @Schema(description = "Приоритет", example = "High")
    private TaskPriority priority;
}
