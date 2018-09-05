package com.booking.event.exception;

public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException() {
        super("Artist not found");
    }
}
