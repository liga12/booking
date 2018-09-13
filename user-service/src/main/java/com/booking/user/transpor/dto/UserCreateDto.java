package com.booking.user.transpor.dto;

import com.booking.user.persistence.entity.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    private UserType type;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Long paymentId;
}
