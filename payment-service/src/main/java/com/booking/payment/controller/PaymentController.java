package com.booking.payment.controller;

import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PutMapping
    public Long create(@RequestBody @Valid PaymentCreateDto dto) {
        return paymentService.create(dto);
    }
}
