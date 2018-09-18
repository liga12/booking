package com.booking.event.transport.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationOutcomeDto {

    private Long id;

    private String name;

    private String location;

    private String phone;

    private String email;

    private Boolean visible = false;

    private Set<Long> events;

    private String customerId;

    public OrganizationOutcomeDto(Long id) {
        this.id = id;
    }
}
