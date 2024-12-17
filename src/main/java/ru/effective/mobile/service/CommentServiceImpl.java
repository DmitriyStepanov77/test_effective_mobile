package ru.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.mobile.dto.comment.NewCommentDto;
import ru.effective.mobile.dto.mapper.CommentMapper;
import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.Task;
import ru.effective.mobile.repository.CommentRepository;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TaskService taskService;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    /**
     * Добавление комментария
     *
     * @param taskId     id задачи, для которой добавляется комментарий
     * @param commentDto DTO объект, содержащий данные о комментарии
     * @param username   имя пользователя, который добавляет комментарий
     * @return созданный комментарий
     */
    @Override
    public Comment createComment(NewCommentDto commentDto, Long taskId, String username) {
        Task task = taskService.getTask(taskId, username);
        Comment comment = commentMapper.toEntity(commentDto);
        log.info("Create comment to task with id = {}, by author = {}.", task.getId(), username);
        comment.setTask(task);
        comment.setAuthor(userService.getUserByUsername(username));
        comment.setCreated(LocalDateTime.now());

        return commentRepository.save(comment);
    }

}
