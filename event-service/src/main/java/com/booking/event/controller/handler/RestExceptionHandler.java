package com.booking.event.controller.handler;

import com.booking.event.exception.AbstractEventNotFoundException;
import com.booking.event.exception.OrganizationNameExistException;
import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.exception.PlaceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = OrganizationNameExistException.class)
    private String organizationNameExist(OrganizationNameExistException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = OrganizationNotFoundException.class)
    private String organizationNotFound(OrganizationNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = AbstractEventNotFoundException.class)
    private String abstractEventnNotFound(AbstractEventNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = PlaceNotFoundException.class)
    private String placeNotFoundException(PlaceNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
