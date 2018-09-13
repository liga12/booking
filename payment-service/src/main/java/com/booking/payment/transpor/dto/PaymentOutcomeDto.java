package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PaymentOutcomeDto {

    private Long id;

    private String token;

    private Set<Long> client;

    private Set<Long> customer;
}
