package com.booking.event.transport.mapper;

import com.booking.event.dto.event.*;
import com.booking.event.persistence.entity.event.*;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import com.booking.event.transport.dto.event.ageConstrain.theatre.TheatreEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.theatre.TheatreEventUpdateDto;
import com.booking.event.transport.dto.event.coverConcert.CoverConcertEventCreateDto;
import com.booking.event.transport.dto.event.coverConcert.CoverConcertEventUpdateDto;
import com.booking.event.transport.dto.event.originalConcert.OriginalConcertEventCreateDto;
import com.booking.event.transport.dto.event.originalConcert.OriginalConcertEventUpdateDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Getter
public abstract class AbstractEventMapper {

    protected OrganizationMapper organizationMapper;

    protected OrganizationService organizationService;

    protected PlaceService placeService;

    @Autowired
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Autowired
    public void setAbstractEventService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public AbstractEvent toEntity(AbstractEventCreateDto dto) {
        if (dto instanceof OriginalConcertEventCreateDto) {
            return toEntity((OriginalConcertEventCreateDto) dto);
        }
        if (dto instanceof CoverConcertEventCreateDto) {
            return toEntity((CoverConcertEventCreateDto) dto);
        }
        if (dto instanceof CinemaEventCreateDto) {
            return toEntity((CinemaEventCreateDto) dto);
        }
        return toEntity((TheatreEventCreateDto) dto);
    }

    public AbstractEvent toEntity(AbstractEventOutcomeDto dto) {
        if (dto instanceof OriginalConcertEventOutcomeDto) {
            return toEntity((OriginalConcertEventOutcomeDto) dto);
        }
        if (dto instanceof CoverConcertEventOutcomeDto) {
            return toEntity((CoverConcertEventOutcomeDto) dto);
        }
        if (dto instanceof CinemaEventOutcomeDto) {
            return toEntity((CinemaEventOutcomeDto) dto);
        }
        return toEntity((TheatreEventOutcomeDto) dto);
    }

    public AbstractEvent toEntity(AbstractEventUpdateDto dto) {
        if (dto instanceof OriginalConcertEventUpdateDto) {
            return toEntity((OriginalConcertEventUpdateDto) dto);
        }
        if (dto instanceof CoverConcertEventUpdateDto) {
            return toEntity((CoverConcertEventUpdateDto) dto);
        }
        if (dto instanceof CinemaEventUpdateDto) {
            return toEntity((CinemaEventUpdateDto) dto);
        }
        return toEntity((TheatreEventUpdateDto) dto);
    }

    public AbstractEventOutcomeDto toDto(AbstractEvent abstractEvent) {
        if (abstractEvent instanceof OriginalConcertEvent) {
            return toDto((OriginalConcertEvent) abstractEvent);
        }
        if (abstractEvent instanceof CoverConcertEvent) {
            return toDto((CoverConcertEvent) abstractEvent);
        }
        if (abstractEvent instanceof CinemaEvent) {
            return toDto((CinemaEvent) abstractEvent);
        }
        return toDto((TheatreEvent) abstractEvent);
    }

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract OriginalConcertEvent toEntity(OriginalConcertEventCreateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract CoverConcertEvent toEntity(CoverConcertEventCreateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract CinemaEvent toEntity(CinemaEventCreateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract TheatreEvent toEntity(TheatreEventCreateDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract OriginalConcertEvent toEntity(OriginalConcertEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract CoverConcertEvent toEntity(CoverConcertEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract CinemaEvent toEntity(CinemaEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract TheatreEvent toEntity(TheatreEventOutcomeDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract OriginalConcertEvent toEntity(OriginalConcertEventUpdateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract CoverConcertEvent toEntity(CoverConcertEventUpdateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract CinemaEvent toEntity(CinemaEventUpdateDto dto);

    @Mapping(target = "organization",
            expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))")
    abstract TheatreEvent toEntity(TheatreEventUpdateDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(originalConcertEvent.getOrganization().getId())"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(originalConcertEvent.getPlaces()))")
    })
    abstract OriginalConcertEventOutcomeDto toDto(OriginalConcertEvent originalConcertEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(coverConcertEvent.getOrganization().getId())"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(coverConcertEvent.getPlaces()))")
    })
    abstract CoverConcertEventOutcomeDto toDto(CoverConcertEvent coverConcertEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(cinemaEvent.getOrganization().getId())"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(cinemaEvent.getPlaces()))")
    })
    abstract CinemaEventOutcomeDto toDto(CinemaEvent cinemaEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(theatreEvent.getOrganization().getId())"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(theatreEvent.getPlaces()))")
    })
    abstract TheatreEventOutcomeDto toDto(TheatreEvent theatreEvent);
}
