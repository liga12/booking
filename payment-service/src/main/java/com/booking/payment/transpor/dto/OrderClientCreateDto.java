package com.booking.payment.transpor.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderClientCreateDto {

    @NotNull
    private Long placeId;

    @NotNull
    private Long paymentClient;

    @NotNull
    private Long paymentCustomer;
}
