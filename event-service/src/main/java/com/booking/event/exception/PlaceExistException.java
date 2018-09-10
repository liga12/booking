package com.booking.event.exception;

public class PlaceExistException extends RuntimeException {

    public PlaceExistException() {
        super("Place exist");
    }
}
