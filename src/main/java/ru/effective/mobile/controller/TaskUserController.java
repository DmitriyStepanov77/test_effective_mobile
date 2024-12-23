package ru.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.dto.task.TaskDto;
import ru.effective.mobile.dto.task.UpdateTaskUserDto;
import ru.effective.mobile.service.TaskService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskUserController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{taskId}")
    @Operation(summary = "Получение задачи по id")
    public TaskDto getTask(@PathVariable Long taskId, Principal principal) {
        return taskMapper.toModel(taskService.getTask(taskId, principal.getName()));
    }

    @GetMapping
    @Operation(summary = "Получение всех задач назначенных пользователю")
    public List<TaskDto> getTasks(Principal principal,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        return taskService.getTasksByPerformer(principal.getName(), from, size).stream()
                .map(taskMapper::toModel).toList();
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "Обновление задачи пользователем")
    public TaskDto updateTask(@PathVariable Long taskId,
                              @Valid @RequestBody UpdateTaskUserDto updateTaskUserDto,
                              Principal principal) {
        return taskMapper.toModel(taskService.updateTaskByUser(taskId, updateTaskUserDto, principal.getName()));
    }
}
