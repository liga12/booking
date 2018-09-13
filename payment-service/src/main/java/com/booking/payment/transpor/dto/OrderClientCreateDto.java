package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderClientCreateDto {

    @NotNull
    private Long placeId;

    @NotNull
    private Long paymentClient;

    @NotNull
    private Long paymentCustomer;
}
