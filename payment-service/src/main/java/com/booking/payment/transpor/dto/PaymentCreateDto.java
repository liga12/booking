package com.booking.payment.transpor.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentCreateDto {

    @Size(min = 6, max = 6)
    @NotBlank
    private String token;
}
