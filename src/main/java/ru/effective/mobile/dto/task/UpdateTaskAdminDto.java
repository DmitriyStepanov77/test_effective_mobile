package ru.effective.mobile.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.model.enums.TaskPriority;
import ru.effective.mobile.model.enums.TaskState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание новой задачи")
public class UpdateTaskAdminDto {
    @Schema(description = "Заголовок задачи", example = "Big task")
    @Size(min = 5, max = 50, message = "Заголовок задачи должнен содержать от 5 до 50 символов")
    private String title;
    @Schema(description = "Описание задачи", example = "To urgently complete a big task")
    @Size(min = 5, max = 255, message = "Описание задачи должно содержать от 5 до 255 символов")
    private String description;
    @Schema(description = "Имя исполнителя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя исполнителя должно содержать от 5 до 50 символов")
    private String performer;
    @Schema(description = "Приоритет задачи", example = "HIGH")
    private TaskPriority priority;
    @Schema(description = "Статус задачи", example = "NEW")
    private TaskState state;
}
