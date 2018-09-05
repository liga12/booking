package com.booking.event.service;

import com.booking.event.exception.AbstractEventNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.AbstractEventRepository;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import com.booking.event.transport.mapper.AbstractEventMapper;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbstractEventServiceImpl implements AbstractEventService {

    private final AbstractEventRepository abstractEventRepository;

    private final AbstractEventMapper abstractEventMapper;

    private final OrganizationService organizationService;

    private final OrganizationMapper organizationMapper;

    @Override
    public Long create(Long organizationId, AbstractEventCreateDto dto) {
        Organization organization = organizationMapper.toEntity(
                organizationService.getById(organizationId)
        );
        AbstractEvent abstractEvent = abstractEventMapper.toEntity(dto);
        abstractEvent.setOrganization(organization);
        return abstractEventRepository.save(abstractEvent).getId();
    }

    @Override
    public AbstractEventOutcomeDto getById(Long id) {
        return abstractEventMapper.toDto(
                abstractEventRepository
                        .findById(id)
                        .orElseThrow(AbstractEventNotFoundException::new)
        );
    }
}
