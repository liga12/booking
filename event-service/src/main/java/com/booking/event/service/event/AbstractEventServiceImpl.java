package com.booking.event.service.event;

import com.booking.event.exception.AbstractEventNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.AbstractEventRepository;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import com.booking.event.transport.mapper.AbstractEventMapper;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class AbstractEventServiceImpl implements AbstractEventService {

    private final AbstractEventRepository abstractEventRepository;

    private AbstractEventMapper abstractEventMapper;

    private OrganizationService organizationService;

    private OrganizationMapper organizationMapper;

    private PlaceService placeService;

    @Autowired
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Autowired
    public void setAbstractEventMapper(AbstractEventMapper abstractEventMapper) {
        this.abstractEventMapper = abstractEventMapper;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    @Transactional
    public Long create(Long organizationId, AbstractEventCreateDto dto) {
        Organization organization = organizationMapper.toEntity(
                organizationService.getById(organizationId)
        );
        AbstractEvent abstractEvent = abstractEventMapper.toEntity(dto);
        abstractEvent.setOrganization(organization);
        return abstractEventRepository.save(abstractEvent).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public AbstractEventOutcomeDto getById(Long id) {
        return abstractEventMapper.toDto(
                abstractEventRepository
                        .findById(id)
                        .orElseThrow(AbstractEventNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Set<AbstractEvent> getById(Set<Long> ids) {
        return new HashSet<>(
                abstractEventRepository
                        .findAllById(ids)
        );
    }

    @Override
    public Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents) {
        Set<Long> eventIds = new HashSet<>();
        for (AbstractEvent abstractEvent : abstractEvents) {
            eventIds.add(abstractEvent.getId());
        }
        return eventIds;
    }
}
