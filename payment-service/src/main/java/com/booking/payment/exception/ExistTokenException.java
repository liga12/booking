package com.booking.payment.exception;

public class ExistTokenException extends RuntimeException {

    public ExistTokenException() {
        super("Token exist");
    }
}
