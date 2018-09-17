package com.booking.payment.transpor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderClientCreateDto {

    @NotNull
    private Long placeId;

    @NotNull
    private Long paymentClient;

    @NotNull
    private Long paymentCustomer;

    public OrderClientCreateDto(@NotNull Long placeId, @NotNull Long paymentClient, @NotNull Long paymentCustomer) {
        this.placeId = placeId;
        this.paymentClient = paymentClient;
        this.paymentCustomer = paymentCustomer;
    }
}
