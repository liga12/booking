package com.booking.payment.transpor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class OrderClientCreateDto {

    @NotNull
    private Long placeId;

    @NotNull
    private Long paymentClient;

    @NotNull
    private Long paymentCustomer;
}
