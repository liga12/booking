package com.booking.user.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException() {
        super("Email exist");
    }
}
