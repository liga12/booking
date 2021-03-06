package com.booking.user.transpor.dto;

import com.booking.user.persistence.entity.UserType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    private UserType type;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Long paymentId;

    public UserCreateDto(@Email String email, @NotNull Long paymentId) {
        this.email = email;
        this.paymentId = paymentId;
    }
}
