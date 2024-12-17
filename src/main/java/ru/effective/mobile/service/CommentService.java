package ru.effective.mobile.service;

import ru.effective.mobile.dto.comment.NewCommentDto;
import ru.effective.mobile.model.Comment;

public interface CommentService {
    public Comment createComment(NewCommentDto commentDto, Long taskId, String username);
}
