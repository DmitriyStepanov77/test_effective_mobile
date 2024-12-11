package ru.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.task.TaskDto;
import ru.effective.mobile.service.TaskServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {
    private final TaskServiceImpl taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    @Operation(summary = "Добавление новой задачи")
    public TaskDto addTask(@RequestBody NewTaskDto taskDto, Principal principal) {
        return taskMapper.toModel(taskService.createTask(taskDto, principal.getName()));
    }

    @GetMapping
    @Operation(summary = "Получение задачи по id")
    public TaskDto getTask(@RequestBody NewTaskDto taskDto, Principal principal) {
        return taskMapper.toModel(taskService.createTask(taskDto, principal.getName()));
    }
}
