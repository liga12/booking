package com.booking.event.exception;

public class OrganizationNameExistException extends RuntimeException {

    public OrganizationNameExistException() {
        super("Name exist");
    }
}
