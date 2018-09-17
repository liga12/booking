package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderClientOutcomeDto {

    private Long id;

    private Long placeId;

    private Long paymentClient;

    private Long paymentCustomer;

    private Double amount;

    public OrderClientOutcomeDto(Long id, Long placeId, Long paymentClient, Long paymentCustomer, Double amount) {
        this.id = id;
        this.placeId = placeId;
        this.paymentClient = paymentClient;
        this.paymentCustomer = paymentCustomer;
        this.amount = amount;
    }
}
