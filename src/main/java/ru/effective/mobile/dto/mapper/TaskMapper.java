package ru.effective.mobile.dto.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.task.TaskDto;
import ru.effective.mobile.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "performer", ignore = true)
    public Task toEntity(NewTaskDto taskDto);

    @Mapping(source = "author.username", target = "author")
    @Mapping(source = "performer.username", target = "performer")
    public TaskDto toModel(Task task);

}
