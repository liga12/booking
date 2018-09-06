package com.booking.event.service;

import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.entity.Artist;
import com.booking.event.persistence.entity.Place;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.transport.dto.PlaceCreateDto;
import com.booking.event.transport.dto.PlaceOutcomeDto;
import com.booking.event.transport.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceMapper placeMapper;

    @Override
    public Long create(PlaceCreateDto dto) {
        return placeRepository.save(
                placeMapper.toEntity(dto)
        ).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PlaceOutcomeDto getById(Long id) {
        return placeMapper.toDto(
                placeRepository
                        .findById(id)
                        .orElseThrow(OrganizationNotFoundException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Place> getById(Set<Long> id) {
        return new HashSet<>(
                placeRepository.findAllById(id)
        );
    }

    @Override
    public Set<Long> getIdFromEntity(Set<Place> places) {
        Set<Long> ids = new HashSet<>();
        for (Place place : places) {
            ids.add(place.getId());
        }
        return ids;
    }
}
