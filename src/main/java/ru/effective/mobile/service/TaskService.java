package ru.effective.mobile.service;

import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.task.UpdateTaskAdminDto;
import ru.effective.mobile.dto.task.UpdateTaskUserDto;
import ru.effective.mobile.model.Task;

import java.util.List;

public interface TaskService {
    public Task createTask(NewTaskDto newTaskDto, String username);

    public Task getTask(Long taskId, String username);

    public Task updateTaskByUser(Long taskId, UpdateTaskUserDto updateTaskUserDto, String username);

    public Task updateTaskByAdmin(Long taskId, UpdateTaskAdminDto updateTaskAdminDto, String username);

    public void deleteTask(Long taskId, String userName);

    public List<Task> getTasksByAuthor(String author, int from, int size);

    public List<Task> getTasksByPerformer(String performer, int from, int size);
}
