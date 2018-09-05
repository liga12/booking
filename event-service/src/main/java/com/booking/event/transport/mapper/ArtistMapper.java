package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Artist;
import com.booking.event.transport.dto.ArtistCreateDto;
import com.booking.event.transport.dto.ArtistOutcomeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistOutcomeDto toDto(Artist artist);

    Artist toEntity(ArtistOutcomeDto dto);

    Artist toEntity(ArtistCreateDto dto);
}
