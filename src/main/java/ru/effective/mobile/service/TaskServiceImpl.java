package ru.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    /**
     * Создание задачи
     *
     * @param newTaskDto DTO объект, содержащий данные о задачи
     * @param username   имя пользователя, который добавляет задачу
     * @return созданная задача
     */
    @Override
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

    /**
     * Получение задачи по id
     *
     * @param taskId   id задачи
     * @param username имя пользователя, который получает задачу
     * @return задача
     */
    @Override
    public Task getTask(Long taskId, String username) {
        log.info("Get task by id = {}, by user = {}.", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if (user.getRole() != UserRole.ROLE_ADMIN && task.getPerformer() != user)
            throw new ConflictException("Пользователь не является админом или исполнителем задачи.");
        return task;
    }

    /**
     * Обновление задачи пользователем по id
     *
     * @param taskId            id задачи
     * @param updateTaskUserDto DTO объект, содержащий данные об обновленной задачи
     * @param username          имя пользователя, который обновляет задачу
     * @return задача
     */
    @Override
    public Task updateTaskByUser(Long taskId, UpdateTaskUserDto updateTaskUserDto, String username) {
        log.info("Update task by id = {}, by user = {}.", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if (task.getPerformer() != user)
            throw new ConflictException("Пользователь не является исполнителем задачи.");
        task.setState(updateTaskUserDto.getState());
        return taskRepository.save(task);
    }

    /**
     * Обновление задачи администратором по id
     *
     * @param taskId             id задачи
     * @param updateTaskAdminDto DTO объект, содержащий данные об обновленной задачи
     * @param username           имя пользователя, который обновляет задачу
     * @return задача
     */
    @Override
    public Task updateTaskByAdmin(Long taskId, UpdateTaskAdminDto updateTaskAdminDto, String username) {
        log.info("Update task by id = {}, by admin = {}.", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Задачи с таким id нет."));
        User user = userService.getUserByUsername(username);
        if (task.getPerformer() != user)
            throw new ConflictException("Пользователь не является администратором.");

        Optional.ofNullable(updateTaskAdminDto.getTitle()).ifPresent(task::setTitle);
        Optional.ofNullable(updateTaskAdminDto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(updateTaskAdminDto.getPriority()).ifPresent(task::setPriority);
        Optional.ofNullable(updateTaskAdminDto.getState()).ifPresent(task::setState);

        if (updateTaskAdminDto.getPerformer() != null)
            task.setPerformer(userService.getUserByUsername(updateTaskAdminDto.getPerformer()));

        return taskRepository.save(task);
    }

    /**
     * Удаление задачи по id
     *
     * @param taskId id задачи
     */
    @Override
    public void deleteTask(Long taskId, String userName) {
        log.info("Delete task by id = {}, by user = {}.", taskId, userName);
        taskRepository.deleteById(taskId);
    }

    /**
     * Получение всех задач, автором которых является заданный пользователь
     *
     * @param author имя заданного пользователя
     * @return список задач
     */
    @Override
    public List<Task> getTasksByAuthor(String author, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return taskRepository.findAllByAuthorId(userService.getUserByUsername(author).getId(), pageable);
    }

    /**
     * Получение всех задач, исполнителем которых является заданный пользователь
     *
     * @param performer имя заданного пользователя
     * @return список задач
     */
    @Override
    public List<Task> getTasksByPerformer(String performer, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return taskRepository.findAllByPerformerId(userService.getUserByUsername(performer).getId(), pageable);
    }

}
