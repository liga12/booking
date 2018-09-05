package com.booking.event.service;

import com.booking.event.transport.dto.OrganizationCreateDto;
import com.booking.event.transport.dto.OrganizationOutcomeDto;

public interface OrganizationService  {

    Long create(OrganizationCreateDto dto);

    OrganizationOutcomeDto getById(Long id);
}
