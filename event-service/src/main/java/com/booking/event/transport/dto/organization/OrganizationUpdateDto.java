package com.booking.event.transport.dto.organization;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrganizationUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @Size(min = 10, max = 10)
    @NotBlank
    private String phone;

    @Email
    private String email;
}
