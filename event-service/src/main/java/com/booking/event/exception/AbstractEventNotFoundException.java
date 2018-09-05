package com.booking.event.exception;

public class AbstractEventNotFoundException extends RuntimeException {

    public AbstractEventNotFoundException() {
        super("AbstractEvent not found");
    }
}
