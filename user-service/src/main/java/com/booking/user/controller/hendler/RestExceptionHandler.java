package com.booking.user.controller.hendler;

import com.booking.user.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = EmailExistException.class)
    private String emailExistException(EmailExistException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = TokenNotExistException.class)
    private String tokenNotExistException(TokenNotExistException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    private String userNotFoundException(UserNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = EventNotFoundException.class)
    private String eventNotFoundException(EventNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = PlaceNotFoundException.class)
    private String placeNotFoundException(PlaceNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = OrganizationNotFoundException.class)
    private String organizationNotFoundException(OrganizationNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    private String fileNotFoundException(FileNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
