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

    @ExceptionHandler(value = PlaceNotFoundException.class)
    private String placeNotFoundException(PlaceNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = PlaceExistException.class)
    private String placeExistException(PlaceExistException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = NotCorrectDateException.class)
    private String notCorrectDateException(NotCorrectDateException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = OrganizationNotActiveException.class)
    private String organizationNotActiveException(OrganizationNotActiveException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = EventNotActiveException.class)
    private String eventNotActiveException(EventNotActiveException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    private String userNotFoundException(CustomerNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
