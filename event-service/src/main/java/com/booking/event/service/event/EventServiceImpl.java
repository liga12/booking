package com.booking.event.service.event;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.exception.AbstractEventNotFoundException;
import com.booking.event.exception.EventNotActiveException;
import com.booking.event.exception.NotCorrectDateException;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import com.booking.event.transport.mapper.EventMapper;
import com.booking.event.transport.mapper.OrganizationMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private EventMapper eventMapper;

    private OrganizationService organizationService;

    private OrganizationMapper organizationMapper;

    private PlaceService placeService;

    @Autowired
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
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
    public Page<AbstractEventOutcomeDto> getAll(EventFindDto dto, Pageable pageable) {
        Page<AbstractEvent> result = eventRepository.findAll(
                EventSearchSpecification.eventFilter(dto),
                pageable
        );
        return result.map(eventMapper::toDto);
    }

    @Override
    public AbstractEventOutcomeDto getById(Long id) {
        return eventMapper.toDto(
                eventRepository
                        .findById(id)
                        .orElseThrow(AbstractEventNotFoundException::new)
        );
    }

    @Override
    public Set<AbstractEvent> getById(Set<Long> ids) {
        List<AbstractEvent> events = eventRepository.findAllById(ids);
        if (events.size() != ids.size()) {
            throw new AbstractEventNotFoundException();
        }
        return new HashSet<>(events);
    }

    @Override
    public Long create(AbstractEventCreateDto dto) {
        validateDate(dto.getDate());
        organizationService.validateOrganizationByActive(
                dto.getOrganization()
        );
        return eventRepository.save(
                eventMapper.toEntity(dto)).getId();
    }

    @Override
    public Long update(AbstractEventUpdateDto dto) {
        validateDate(dto.getDate());
        validateExistById(dto.getId());
        organizationService.validateOrganizationByActive(
                dto.getOrganization()
        );
        return eventRepository.save(
                eventMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public void delete(Long id) {
        AbstractEvent abstractEvent = eventMapper.toEntity(getById(id));
        for (Place place : abstractEvent.getPlaces()) {
            placeService.delete(place.getId());
        }
        abstractEvent.setVisible(false);
        eventRepository.save(abstractEvent);
    }

    @Override
    public Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents) {
        Set<Long> eventIds = new HashSet<>();
        for (AbstractEvent abstractEvent : abstractEvents) {
            eventIds.add(abstractEvent.getId());
        }
        return eventIds;
    }

    @Override
    public void validateEventByActive(Long id) {
        AbstractEventOutcomeDto abstractEventOutcomeDto = getById(id);
        long timestamp = System.currentTimeMillis() / 1000;
        if (abstractEventOutcomeDto.getDate() <= timestamp) {
            delete(id);
        }
        if (!abstractEventOutcomeDto.getVisible()) {
            throw new EventNotActiveException();
        }
    }

    @Override
    public boolean existById(Long id) {
        return eventRepository.existsById(id);
    }

    private void validateExistById(Long id) {
        if (id == null || !eventRepository.existsById(id)) {
            throw new AbstractEventNotFoundException();
        }
    }

    private void validateDate(Long date) {
        long timestamp = System.currentTimeMillis() / 1000;
        if (date <= timestamp) {
            throw new NotCorrectDateException();
        }
    }
}
