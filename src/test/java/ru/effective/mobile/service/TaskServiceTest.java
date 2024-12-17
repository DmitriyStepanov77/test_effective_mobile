package ru.effective.mobile.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.effective.mobile.dto.mapper.TaskMapper;
import ru.effective.mobile.dto.task.NewTaskDto;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.model.User;
import ru.effective.mobile.model.enums.TaskPriority;
import ru.effective.mobile.model.enums.UserRole;
import ru.effective.mobile.repository.TaskRepository;
import ru.effective.mobile.repository.UserRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"db.name=test"})
public class TaskServiceTest {

    @InjectMocks
    TaskServiceImpl taskService;

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    TaskMapper taskMapper;

    User user;
    Task task;
    Task taskDtoEntity;
    NewTaskDto taskDto;

    @BeforeEach
    void setup(){
        user = new User();
        user.setId(1L);
        user.setEmail("1@mail.ru");
        user.setUsername("user");
        user.setPassword("123");
        user.setRole(UserRole.ROLE_ADMIN);

        task = new Task();
        task.setId(1L);
        task.setAuthor(user);
        task.setTitle("123");
        task.setDescription("12345");
        task.setPerformer(user);
        task.setPriority(TaskPriority.HIGH);

        taskDto = new NewTaskDto();
        taskDto.setTitle("123");
        taskDto.setDescription("12345");
        taskDto.setPerformer("user");
        taskDto.setPriority(TaskPriority.HIGH);

        taskDtoEntity = new Task();
        taskDtoEntity.setTitle("123");
        taskDtoEntity.setDescription("12345");
        taskDtoEntity.setPerformer(user);
        taskDtoEntity.setPriority(TaskPriority.HIGH);
    }

    @Test
    void addTaskTest() {
        Mockito.when(taskRepository.save(any())).thenReturn(task);
        Mockito.when(userService.getUserByUsername(anyString())).thenReturn(user);
        Mockito.when(taskMapper.toEntity(any())).thenReturn(taskDtoEntity);

        Task savedTask = taskService.createTask(taskDto, user.getUsername());

        assertEquals(task.getDescription(), savedTask.getDescription());
    }

    @Test
    void getTaskTest(){
        Mockito.when(taskRepository.findById(anyLong())).thenReturn(Optional.ofNullable(task));
        Mockito.when(userService.getUserByUsername(anyString())).thenReturn(user);

        Task getTask = taskService.getTask(1L, user.getUsername());

        assertEquals(task.getDescription(), getTask.getDescription());

    }
}
