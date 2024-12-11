package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective.mobile.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
