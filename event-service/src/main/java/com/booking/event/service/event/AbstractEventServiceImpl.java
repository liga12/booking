package com.booking.event.service.event;

import com.booking.event.exception.AbstractEventNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.AbstractEventRepository;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventFindDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import com.booking.event.transport.mapper.AbstractEventMapper;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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
    @Transactional(readOnly = true)
    public Page<AbstractEventOutcomeDto> getAll(AbstractEventFindDto dto, Pageable pageable) {
        Page<AbstractEvent> result = abstractEventRepository.findAll(
                EventSearchSpecification.eventFilter(dto),
                pageable
        );
        return result.map(abstractEventMapper::toDto);
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
        List<AbstractEvent> events = abstractEventRepository.findAllById(ids);
        if (events.size()!=ids.size()){
            throw  new AbstractEventNotFoundException();
        }
        return new HashSet<>(events);
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
    @Transactional
    public Long update(Long id, AbstractEventUpdateDto dto) {
        validateExistById(id);
        Organization organization = organizationMapper.toEntity(
                organizationService.getById(
                        dto.getOrganization()
                )
        );
        AbstractEvent abstractEvent = abstractEventMapper.toEntity(dto);
        abstractEvent.setOrganization(organization);
        return abstractEventRepository.save(
                abstractEvent
        ).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AbstractEvent abstractEvent = abstractEventMapper.toEntity(getById(id));
        abstractEvent.setVisible(false);
        abstractEventRepository.save(abstractEvent);
    }

    @Override
    public Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents) {
        Set<Long> eventIds = new HashSet<>();
        for (AbstractEvent abstractEvent : abstractEvents) {
            eventIds.add(abstractEvent.getId());
        }
        return eventIds;
    }

    private void validateExistById(Long id) {
        if (id == null || !abstractEventRepository.existsById(id)) {
            throw new AbstractEventNotFoundException();
        }
    }
}
