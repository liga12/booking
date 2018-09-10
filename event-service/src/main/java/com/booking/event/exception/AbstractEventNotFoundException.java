package com.booking.event.exception;

public class AbstractEventNotFoundException extends RuntimeException {

    public AbstractEventNotFoundException() {
        super("Event not found");
    }
}
