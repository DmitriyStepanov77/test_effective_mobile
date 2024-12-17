package ru.effective.mobile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.effective.mobile.exception.NotFoundException;
import ru.effective.mobile.model.User;
import ru.effective.mobile.model.enums.UserRole;
import ru.effective.mobile.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    @Override
    public User createUser(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new NotFoundException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new NotFoundException("Пользователь с таким email уже существует");
        }

        return saveUser(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    @Override
    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем " + username + " не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    @Override
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(UserRole.ROLE_ADMIN);
        saveUser(user);
    }
}