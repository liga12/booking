package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.place.Place;
import com.booking.event.service.event.AbstractEventService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceOutcomeDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
public abstract class PlaceMapper {

    AbstractEventService abstractEventService;

    @Autowired
    public void setPlaceService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @Mapping(target = "abstractEvents", expression = "java(abstractEventService.getById(dto.getAbstractEvents()))")
    public abstract Place toEntity(PlaceOutcomeDto dto);

    @Mapping(target = "rowType", source = "rowType")
    public abstract Place toEntity(PlaceCreateDto dto);

    @Mapping(target = "abstractEvents", expression = "java(abstractEventService.getIdFromEntity(place.getAbstractEvents()))")
    public abstract PlaceOutcomeDto toDto(Place place);
}
