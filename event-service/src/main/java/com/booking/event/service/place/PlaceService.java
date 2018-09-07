package com.booking.event.service.place;

import com.booking.event.persistence.entity.place.Place;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceOutcomeDto;

import java.util.Set;

public interface PlaceService {

    Long create(PlaceCreateDto dto);

    PlaceOutcomeDto getById(Long id);

    Set<Place> getById(Set<Long> id);

    Set<Long> getIdFromEntity(Set<Place> places);
}
