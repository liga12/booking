package com.booking.payment.transpor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderClientOutcomeDto {

    private Long id;

    private Long placeId;

    private Long paymentClient;

    private Long paymentCustomer;

    private Double amount;
}
