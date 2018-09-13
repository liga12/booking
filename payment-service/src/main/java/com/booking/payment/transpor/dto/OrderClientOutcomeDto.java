package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderClientOutcomeDto {

    private Long Id;

    private Long placeId;

    private Long paymentClient;

    private Long paymentCustomer;

    private Integer amount;
}
