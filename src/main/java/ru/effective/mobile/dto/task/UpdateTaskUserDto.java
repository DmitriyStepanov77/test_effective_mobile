package ru.effective.mobile.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.model.enums.TaskState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Редактирование задачи исполнителем")
public class UpdateTaskUserDto {
    @Schema(description = "Статус задачи", example = "NEW")
    private TaskState state;
}
