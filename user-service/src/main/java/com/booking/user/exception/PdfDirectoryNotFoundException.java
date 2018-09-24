package com.booking.user.exception;

public class PdfDirectoryNotFoundException extends RuntimeException {

    public PdfDirectoryNotFoundException() {
        super("Pdf directory not found");
    }
}
