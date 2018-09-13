package com.booking.payment.controller;

import com.booking.payment.service.order.OrderClientService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderClientController {

    @Autowired
    private OrderClientService orderClientService;

    @PutMapping
    public Long buy(@RequestBody OrderClientCreateDto dto){
       return orderClientService.create(dto);

    }
}
