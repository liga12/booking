package com.booking.user.exception;

public class TokenNotExistException extends RuntimeException {

    public TokenNotExistException() {
        super("Token not exist");
    }
}
