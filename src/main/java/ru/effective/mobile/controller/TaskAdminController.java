package ru.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.task.TaskDto;
import ru.effective.mobile.dto.task.UpdateTaskUserDto;
import ru.effective.mobile.service.TaskServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks/admin")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskAdminController {
    private final TaskServiceImpl taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой задачи")
    public TaskDto addTask(@Valid @RequestBody NewTaskDto taskDto, Principal principal) {
        return taskMapper.toModel(taskService.createTask(taskDto, principal.getName()));
    }

    @GetMapping("/author")
    @Operation(summary = "Получение админом всех задач, автором которых является заданный пользователь")
    public List<TaskDto> getTasksByAuthor(@RequestParam String username) {
        return taskService.getTasksByAuthor(username).stream()
                .map(taskMapper::toModel).toList();
    }

    @GetMapping("/performer")
    @Operation(summary = "Получение админом всех задач назначенных пользователю")
    public List<TaskDto> getTasksByPerformer(@RequestParam String username) {
        return taskService.getTasksByPerformer(username).stream()
                .map(taskMapper::toModel).toList();
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление задачи админом")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
