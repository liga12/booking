package com.booking.event.exception;

public class NotCorrectDateException extends RuntimeException {

    public NotCorrectDateException() {
        super("Not correct date");
    }
}
