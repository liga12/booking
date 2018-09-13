package com.booking.payment.controller.handler;

import com.booking.payment.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = ExistTokenException.class)
    private String existTokenException(ExistTokenException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = PlaceNotFoundException.class)
    private String placeNotFound(PlaceNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = PaymentNotFoundException.class)
    private String paymentNotFoundException(PaymentNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    private String customerNotFoundException(CustomerNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    private String clientNotFoundException(ClientNotFoundException e) {
        return String.format("{\"error\":\"%s\"}", e.getMessage());
    }
}
