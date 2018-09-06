package com.booking.event.transport.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrganizationOutcomeDto {

    private Long id;

    private String name;

    private String location;

    private String phone;

    private String email;

    private Boolean visible = false;

    private Set<Long> abstractEvents;
}