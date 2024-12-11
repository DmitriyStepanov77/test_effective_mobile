package ru.effective.mobile.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effective.mobile.dto.security.AuthenticationDto;
import ru.effective.mobile.dto.security.JwtDto;
import ru.effective.mobile.dto.security.RegistrationUserDto;
import ru.effective.mobile.model.enums.UserRole;
import ru.effective.mobile.model.User;
import ru.effective.mobile.service.UserServiceImpl;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtDto signUp(RegistrationUserDto request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = jwtService.generateToken(user);
        return new JwtDto(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtDto signIn(AuthenticationDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtDto(jwt);
    }
}
