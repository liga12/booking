package com.booking.event.exception;

public class EventNotActiveException extends RuntimeException {

    public EventNotActiveException() {
        super("Event not active");
    }
}
