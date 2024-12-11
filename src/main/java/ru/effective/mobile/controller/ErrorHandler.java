package ru.effective.mobile.controller;

import jakarta.xml.bind.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective.mobile.dto.error.ErrorDto;
import ru.effective.mobile.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFound(final NotFoundException e) {
        log.error(e.getMessage());
        return ErrorDto.builder()
                .errors(new ArrayList<>())
                .message(e.getMessage())
                .reason("NotFoundException")
                .status(HttpStatus.NOT_FOUND.name())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleNotFound(final ValidationException e) {
        log.error(e.getMessage());
        return ErrorDto.builder()
                .errors(new ArrayList<>())
                .message(e.getMessage())
                .reason("BadRequest")
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
