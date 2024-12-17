package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective.mobile.model.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findAllByPerformerId(long performerId, Pageable pageable);
    List<Task> findAllByAuthorId(long authorId, Pageable pageable);
}
