package com.booking.payment.transpor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOutcomeDto {

    private Long id;

    private String token;

    private Set<Long> client;

    private Set<Long> customer;


}
