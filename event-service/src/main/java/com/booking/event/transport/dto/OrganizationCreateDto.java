package com.booking.event.transport.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class OrganizationCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @Size(min = 10, max = 10)
    @NotBlank
    private String phone;

    @Email
    private String email;

    @NotNull
    private Boolean visible;
}
