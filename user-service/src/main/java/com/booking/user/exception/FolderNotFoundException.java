package com.booking.user.exception;

public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException(String message) {
        super(message+ "folder not found");
    }
}
