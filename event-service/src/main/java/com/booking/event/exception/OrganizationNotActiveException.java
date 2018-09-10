package com.booking.event.exception;

public class OrganizationNotActiveException extends RuntimeException {

    public OrganizationNotActiveException() {
        super("Organization not active");
    }
}
