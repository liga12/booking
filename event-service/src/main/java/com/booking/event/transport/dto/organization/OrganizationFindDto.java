package com.booking.event.transport.dto.organization;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrganizationFindDto {

    private Long id;

    private String name;

    private String location;

    private String phone;

    private String email;

    private Boolean visible;

    private Set<Long> events;

    private String customerId;
}
