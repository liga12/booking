package com.booking.event.service;

import com.booking.event.persistence.entity.Artist;
import com.booking.event.transport.dto.ArtistCreateDto;
import com.booking.event.transport.dto.ArtistOutcomeDto;

import java.util.Set;

public interface ArtistService {

    ArtistOutcomeDto getById(Long id);

    Long create(ArtistCreateDto dto);

    Set<Artist> getArtistsByIds(Set<Long> ids);

//    void validateByIds(Set<Long> ids);
}
