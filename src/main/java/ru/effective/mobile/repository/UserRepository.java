package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective.mobile.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
