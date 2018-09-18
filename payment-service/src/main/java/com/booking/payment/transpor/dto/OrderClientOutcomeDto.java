package com.booking.payment.transpor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClientOutcomeDto {

    private Long id;

    private Long placeId;

    private Long paymentClient;

    private Long paymentCustomer;

    private Double amount;
}
