package com.booking.event.service;

import com.booking.event.persistence.entity.Place;
import com.booking.event.transport.dto.PlaceCreateDto;
import com.booking.event.transport.dto.PlaceOutcomeDto;

import java.util.Set;

public interface PlaceService {

    Long create(PlaceCreateDto dto);

    PlaceOutcomeDto getById(Long id);

    Set<Place> getById(Set<Long> id);

    Set<Long> getIdFromEntity(Set<Place> places);
}
