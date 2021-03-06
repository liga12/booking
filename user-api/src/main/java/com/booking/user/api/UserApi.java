package com.booking.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user-api")
public interface UserApi {

    @GetMapping("/{customerId}")
    boolean existsCustomer(@PathVariable("customerId") String customerId);

    @GetMapping("customer/{paymentId}")
    boolean existCustomerByPaymentId(@PathVariable("paymentId") Long paymentId);

    @GetMapping("client/{paymentId}")
    boolean existClientByPaymentId(@PathVariable("paymentId") Long paymentId);
}
