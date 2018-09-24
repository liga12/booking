package com.booking.event.transport.dto.organization;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    @NotBlank
    private String email;

    public OrganizationUpdateDto(@NotNull Long id) {
        this.id = id;
    }
}
