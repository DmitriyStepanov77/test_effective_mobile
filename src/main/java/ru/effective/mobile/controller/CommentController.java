package ru.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.dto.comment.CommentDto;
import ru.effective.mobile.dto.comment.NewCommentDto;
import ru.effective.mobile.dto.mapper.CommentMapper;
import ru.effective.mobile.service.CommentService;

import java.security.Principal;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    @Operation(summary = "Добавление комментария к задачи пользователем")
    public CommentDto addComment(@PathVariable Long taskId,
                                 @Valid @RequestBody NewCommentDto commentDto,
                                 Principal principal) {
        return commentMapper.toModel(commentService.createComment(commentDto, taskId, principal.getName()));
    }
}
