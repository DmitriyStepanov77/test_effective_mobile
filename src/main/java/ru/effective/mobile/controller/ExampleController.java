package ru.effective.mobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.mobile.service.UserServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class ExampleController {
    private final UserServiceImpl service;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String example(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public void getAdmin() {
        service.getAdmin();
    }
}
