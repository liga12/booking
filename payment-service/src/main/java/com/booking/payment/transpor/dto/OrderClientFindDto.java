package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderClientFindDto {

    private Long Id;

    private Long placeId;

    private Long paymentClient;

    private Long paymentCustomer;

    private Double amount;
}
