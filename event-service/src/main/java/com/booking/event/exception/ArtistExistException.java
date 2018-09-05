package com.booking.event.exception;

public class ArtistExistException extends RuntimeException {

    public ArtistExistException() {
        super("Artist exist");
    }
}
