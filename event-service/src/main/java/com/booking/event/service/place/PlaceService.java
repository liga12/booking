package com.booking.event.service.place;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface PlaceService {

    @Transactional(readOnly = true)
    Page<PlaceOutcomeDto> getAll(PlaceFindDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    PlaceOutcomeDto getById(Long id);

    @Transactional(readOnly = true)
    Set<Place> getById(Set<Long> id);

    Long create(PlaceCreateDto dto);

    Long update(PlaceUpdateDto dto);

    void buyPlace(Long id);

    void delete(Long id);

    Set<Long> getIdFromEntity(Set<Place> places);

    boolean existActivePlace(Long id);

    boolean existBuyPlace(Long id);
}
