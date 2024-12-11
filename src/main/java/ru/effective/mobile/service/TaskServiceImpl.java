package ru.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.exception.NotFoundException;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.enums.TaskState;
import ru.effective.mobile.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
    }

    public List<Task> getTasksByAuthor(String author) {
        return taskRepository.findAllByAuthorId(userService.getUserByUsername(author).getId());
    }

    public List<Task> getTasksByPerformer(String performer) {
        return taskRepository.findAllByPerformerId(userService.getUserByUsername(performer).getId());
    }

}
