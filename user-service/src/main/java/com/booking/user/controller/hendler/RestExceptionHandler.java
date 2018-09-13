package com.booking.user.controller.hendler;

import com.booking.user.exception.EmailExistException;
import com.booking.user.exception.TokenNotExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
