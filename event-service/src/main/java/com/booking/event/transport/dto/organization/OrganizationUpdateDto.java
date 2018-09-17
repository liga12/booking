package com.booking.event.transport.dto.organization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
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

    public OrganizationUpdateDto(@NotNull Long id) {
        this.id = id;
    }
}
