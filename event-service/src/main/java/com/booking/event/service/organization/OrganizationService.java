package com.booking.event.service.organization;

import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrganizationService {

    @Transactional(readOnly = true)
    Page<OrganizationOutcomeDto> getAll(OrganizationFindDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    OrganizationOutcomeDto getById(Long id);

    Long create(OrganizationCreateDto dto);

    Long update(OrganizationUpdateDto dto);

    void delete(Long id);

    @Transactional(readOnly = true)
    boolean exists(Long id);

    void validateOrganizationByActive(Long id);
}
