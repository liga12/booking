package com.booking.event.service.place;

import com.booking.event.exception.PlaceExistException;
import com.booking.event.exception.PlaceNotFoundException;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.entity.place.PlaceStatusType;
import com.booking.event.persistence.entity.place.SectionType;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceOutcomeDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.transport.mapper.AbstractEventMapper;
import com.booking.event.transport.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceMapper placeMapper;

    @Autowired
    AbstractEventMapper abstractEventMapper;

    @Autowired
    AbstractEventService abstractEventService;

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
        return placeMapper.toDto(
                placeRepository
                        .findById(id)
                        .orElseThrow(PlaceNotFoundException::new)
        );
    }

    @Override
    public Set<Place> getById(Set<Long> id) {
        return new HashSet<>(
                placeRepository.findAllById(id)
        );
    }

    @Override
    public Long create(PlaceCreateDto dto) {
        validatePlace(
                dto.getNumber(),
                dto.getRow(),
                dto.getSectionType(),
                abstractEventMapper.toEntity(abstractEventService.getById(dto.getEvent()))
        );
        abstractEventService.validateEventByActive(dto.getEvent());
        return placeRepository.save(
                placeMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public Long update(PlaceUpdateDto dto) {
        abstractEventService.validateEventByActive(
                abstractEventService.getById(
                        dto.getId()
                ).getId()
        );
        return placeRepository.save(
                placeMapper.toEntity(dto)
        ).getId();
    }

    @Override
    public void delete(Long id) {
        PlaceOutcomeDto placeOutcomeDto = getById(id);
        placeOutcomeDto.setStatus(PlaceStatusType.NOT_ACTIVE);
        placeRepository.save(placeMapper.toEntity(placeOutcomeDto));
    }

    @Override
    public Set<Long> getIdFromEntity(Set<Place> places) {
        Set<Long> ids = new HashSet<>();
        for (Place place : places) {
            ids.add(place.getId());
        }
        return ids;
    }

    private void validatePlace(Integer number, Integer row, SectionType type, AbstractEvent event) {
        if (number == null || row == null || type == null || placeRepository.existsAllByNumberAndAndRowAndSectionTypeAndEvent(
                number,
                row,
                type,
                event)) {
            throw new PlaceExistException();

        }
    }
}
