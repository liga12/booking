package com.booking.payment.controller.handler;

import com.booking.payment.exception.ExistTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = ExistTokenException.class)
    private String existTokenException(ExistTokenException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
