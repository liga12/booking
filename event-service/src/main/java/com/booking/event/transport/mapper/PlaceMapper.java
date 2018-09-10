package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.place.Place;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceOutcomeDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
public abstract class PlaceMapper {

    @Autowired
    AbstractEventMapper abstractEventMapper;

    @Autowired
    PlaceService placeService;

    AbstractEventService abstractEventService;

    @Autowired
    public void setPlaceService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @Mapping(target = "event", expression = "java(abstractEventMapper.toEntity(abstractEventService.getById(dto.getEvent())))")
    public abstract Place toEntity(PlaceCreateDto dto);

    @Mapping(target = "event", expression = "java(abstractEventMapper.toEntity(abstractEventService.getById(dto.getEvent())))")
    public abstract Place toEntity(PlaceOutcomeDto dto);

    @Mapping(target = "number", expression = "java(placeService.getById(dto.getId()).getNumber())")
    @Mapping(target = "row", expression = "java(placeService.getById(dto.getId()).getRow())")
    @Mapping(target = "event", expression = "java(abstractEventMapper.toEntity(abstractEventService.getById(placeService.getById(dto.getId()).getEvent())))")
    @Mapping(target = "sectionType", expression = "java(placeService.getById(dto.getId()).getSectionType())")
    public abstract Place toEntity(PlaceUpdateDto dto);

    @Mapping(target = "event", expression = "java(place.getEvent().getId())")
    public abstract PlaceOutcomeDto toDto(Place place);
}
