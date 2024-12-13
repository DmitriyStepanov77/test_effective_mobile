package ru.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.dto.task.UpdateTaskAdminDto;
import ru.effective.mobile.dto.task.UpdateTaskUserDto;
import ru.effective.mobile.exception.ConflictException;
import ru.effective.mobile.exception.NotFoundException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.model.enums.TaskState;
import ru.effective.mobile.model.enums.UserRole;
import ru.effective.mobile.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl {
    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final TaskMapper taskMapper;

    /**
     * Создание задачи
     *
     * @return созданная задача
     */
    public Task createTask(NewTaskDto newTaskDto, String username) {

        Task task = taskMapper.toEntity(newTaskDto);
        log.info("Create task with title = {}, by author = {} and performer = {}.",
                task.getTitle(), username, newTaskDto.getPerformer());
        task.setAuthor(userService.getUserByUsername(username));
        task.setPerformer(userService.getUserByUsername(newTaskDto.getPerformer()));
        task.setCreated(LocalDateTime.now());
        task.setState(TaskState.NEW);
        return taskRepository.save(task);
    }

    public Task getTask(Long taskId, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if(user.getRole() != UserRole.ROLE_ADMIN && task.getPerformer() != user)
            throw new ConflictException("Пользователь не является админом или исполнителем задачи.");
        return task;
    }

    public Task updateTaskByUser(Long taskId, UpdateTaskUserDto updateTaskUserDto, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if(task.getPerformer() != user)
            throw new ConflictException("Пользователь не является исполнителем задачи.");
        task.setState(updateTaskUserDto.getState());
        return taskRepository.save(task);
    }

    public Task updateTaskByAdmin(Long taskId, UpdateTaskAdminDto updateTaskAdminDto, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if(task.getPerformer() != user)
            throw new ConflictException("Пользователь не является администратором.");

        Optional.ofNullable(updateTaskAdminDto.getTitle()).ifPresent(task::setTitle);
        Optional.ofNullable(updateTaskAdminDto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(updateTaskAdminDto.getPriority()).ifPresent(task::setPriority);
        Optional.ofNullable(updateTaskAdminDto.getState()).ifPresent(task::setState);

        if (updateTaskAdminDto.getPerformer() != null)
            task.setPerformer(userService.getUserByUsername(updateTaskAdminDto.getPerformer()));

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<Task> getTasksByAuthor(String author) {
        return taskRepository.findAllByAuthorId(userService.getUserByUsername(author).getId());
    }

    public List<Task> getTasksByPerformer(String performer) {
        return taskRepository.findAllByPerformerId(userService.getUserByUsername(performer).getId());
    }

}
