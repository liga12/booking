package com.booking.payment.controller;

import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public Page<PaymentOutcomeDto> getAll(PaymentFindDto dto, Pageable pageable) {
        return paymentService.getAll(dto, pageable);
    }

    @PutMapping
    public Long create(@RequestBody @Valid PaymentCreateDto dto) {
        return paymentService.create(dto);
    }
}
