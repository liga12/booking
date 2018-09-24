package com.booking.event.transport.dto.organization;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrganizationCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @Size(min = 10, max = 10)
    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String customerId;
}
