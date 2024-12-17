package ru.effective.mobile.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.effective.mobile.model.User;

public interface UserService {
    public User saveUser(User user);

    public User createUser(User user);

    public User getUserByUsername(String username);

    public UserDetailsService userDetailsService();

    public User getCurrentUser();
}
