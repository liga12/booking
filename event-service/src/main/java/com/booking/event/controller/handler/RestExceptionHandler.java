package com.booking.event.controller.handler;

import com.booking.event.exception.*;
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

    @ExceptionHandler(value = ArtistExistException.class)
    private String artistExistException(ArtistExistException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = ArtistNotFoundException.class)
    private String artistNotFoundException(ArtistNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
