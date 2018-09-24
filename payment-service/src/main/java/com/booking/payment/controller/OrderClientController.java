package com.booking.payment.controller;

import com.booking.payment.service.order.OrderClientService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderClientController {

    private final OrderClientService orderClientService;

    @GetMapping
    public Page<OrderClientOutcomeDto> getAll(OrderClientFindDto dto, Pageable pageable) {
        return orderClientService.getAll(dto, pageable);
    }

    @PutMapping
    public Long buy(@RequestBody @Valid OrderClientCreateDto dto) {
        return orderClientService.create(dto);
    }
}
