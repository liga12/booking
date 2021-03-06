package com.booking.payment.controller;

import com.booking.payment.api.PaymentApi;
import com.booking.payment.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentApiController implements PaymentApi {

    private final PaymentService paymentService;

    @Override
    public boolean existTokenId(@PathVariable Long paymentId) {
        return paymentService.existTokenId(paymentId);
    }
}
