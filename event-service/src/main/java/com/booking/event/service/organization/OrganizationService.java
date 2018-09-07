package com.booking.event.service.organization;

import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

    Page<OrganizationOutcomeDto> getAll(OrganizationFindDto dto, Pageable pageable);

    OrganizationOutcomeDto getById(Long id);

    Long create(OrganizationCreateDto dto);

    Long update(OrganizationUpdateDto dto);

    void delete(Long id);
}
