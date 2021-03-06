package com.booking.event.service.place;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.exception.PlaceExistException;
import com.booking.event.exception.PlaceNotFoundException;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.service.event.EventService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.transport.mapper.EventMapper;
import com.booking.event.transport.mapper.PlaceMapper;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    private PlaceMapper placeMapper;

    private EventMapper eventMapper;

    private EventService eventService;

    @Autowired
    public void setPlaceMapper(PlaceMapper placeMapper) {
        this.placeMapper = placeMapper;
    }

    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public Page<PlaceOutcomeDto> getAll(PlaceFindDto dto, Pageable pageable) {
        Page<Place> result = placeRepository.findAll(
                PlaceSearchSpecification.placeFilter(dto),
                pageable
        );
        return result.map(placeMapper::toDto);
    }

    @Override
    public PlaceOutcomeDto getById(Long id) {
        if (id == null) {
            throw new PlaceNotFoundException();
        }
        return placeMapper.toDto(
                placeRepository
                        .findById(id)
                        .orElseThrow(PlaceNotFoundException::new)
        );
    }

    @Override
    public Set<Place> getById(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        List<Place> places = placeRepository.findAllById(ids);
        if (places.size() != ids.size()) {
            throw new PlaceNotFoundException();
        }
        return new HashSet<>(places);
    }

    @Override
    public Long create(PlaceCreateDto dto) {
        validateCreatePlace(
                dto.getNumber(),
                dto.getRow(),
                dto.getSectionType(),
                eventMapper.toEntity(eventService.getById(dto.getEvent()))
        );
        eventService.validateEventByActive(dto.getEvent());
        return placeRepository.save(
                placeMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public Long update(PlaceUpdateDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new PlaceNotFoundException();
        }
        Place place = placeMapper.toEntity(dto);
        eventService.validateEventByActive(
                eventService.getById(
                        place.getEvent().getId()
                ).getId()
        );
        return placeRepository.save(place).getId();
    }

    @Override
    public void buyPlace(Long id) {
        Place place = placeMapper.toEntity(getById(id));
        if (!place.getStatus().equals(PlaceStatusType.ACTIVE)) {
            throw new PlaceNotFoundException();
        }
        place.setStatus(PlaceStatusType.BYU);
        placeRepository.save(place);
    }

    @Override
    public void delete(Long id) {
        validatePlace(id);
        PlaceOutcomeDto placeOutcomeDto = getById(id);
        placeOutcomeDto.setStatus(PlaceStatusType.NOT_ACTIVE);
        placeRepository.save(placeMapper.toEntity(placeOutcomeDto));
    }

    @Override
    public Set<Long> getIdFromEntity(Set<Place> places) {
        if (places == null) {
            return null;
        }
        Set<Long> ids = new HashSet<>();
        for (Place place : places) {
            ids.add(place.getId());
        }
        return ids;
    }


    @Override
    public boolean existActivePlace(Long id) {
        return id != null && placeRepository.existsByIdAndStatus(id, PlaceStatusType.ACTIVE);
    }

    @Override
    public boolean existBuyPlace(Long id) {
        return id != null && placeRepository.existsByIdAndStatus(id, PlaceStatusType.BYU);

    }

    private void validateCreatePlace(Integer number, Integer row, SectionType type, AbstractEvent event) {
        if (number == null || row == null || type == null || placeRepository.existsAllByNumberAndAndRowAndSectionTypeAndEvent(
                number,
                row,
                type,
                event)) {
            throw new PlaceExistException();

        }
    }

    private void validatePlace(Long id) {
        if (id == null || !placeRepository.existsById(id)) {
            throw new PlaceNotFoundException();
        }
    }
}
