package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Artist;
import com.booking.event.service.AbstractEventService;
import com.booking.event.transport.dto.ArtistCreateDto;
import com.booking.event.transport.dto.ArtistOutcomeDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
public abstract class ArtistMapper {

    AbstractEventService abstractEventService;

    @Autowired
    public void setAbstractEventService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @Mapping(target = "events", expression = "java(abstractEventService.getIdFromEntity(artist.getAbstractEvents()))")
    public abstract ArtistOutcomeDto toDto(Artist artist);

    @Mapping(target = "abstractEvents", expression = "java( abstractEventService.getById(dto.getEvents()))")
    public abstract Artist toEntity(ArtistOutcomeDto dto);

    public abstract Artist toEntity(ArtistCreateDto dto);
}
