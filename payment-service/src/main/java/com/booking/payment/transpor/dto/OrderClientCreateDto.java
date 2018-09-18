package com.booking.payment.transpor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClientCreateDto {

    @NotNull
    private Long placeId;

    @NotNull
    private Long paymentClient;

    @NotNull
    private Long paymentCustomer;
}
