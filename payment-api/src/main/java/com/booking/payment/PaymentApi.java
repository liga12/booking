package com.booking.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/payment-api")
public interface PaymentApi {

    @GetMapping("/{paymentId}")
    boolean existTokenId(@PathVariable("paymentId") Long paymentId);
}
