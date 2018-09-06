package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.event.*;
import com.booking.event.service.ArtistService;
import com.booking.event.service.OrganizationService;
import com.booking.event.service.PlaceService;
import com.booking.event.transport.dto.event.*;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
public abstract class AbstractEventMapper {

    OrganizationMapper organizationMapper;

    OrganizationService organizationService;

    ArtistService artistService;

    PlaceService placeService;

    @Autowired
    public void setPlaceService(PlaceService placeService){
        this.placeService = placeService;
    }

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
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

    abstract OriginalConcertEvent toEntity(OriginalConcertEventCreateDto dto);

    abstract CoverConcertEvent toEntity(CoverConcertEventCreateDto dto);

    abstract CinemaEvent toEntity(CinemaEventCreateDto dto);

    abstract TheatreEvent toEntity(TheatreEventCreateDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getById(dto.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract OriginalConcertEvent toEntity(OriginalConcertEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getById(dto.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract CoverConcertEvent toEntity(CoverConcertEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getById(dto.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract CinemaEvent toEntity(CinemaEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(organizationMapper.toEntity(organizationService.getById(dto.getOrganization())))"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getById(dto.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getById(dto.getPlaces()))")
    })
    abstract TheatreEvent toEntity(TheatreEventOutcomeDto dto);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(originalConcertEvent.getOrganization().getId())"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getIdFromEntity(originalConcertEvent.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(originalConcertEvent.getPlaces()))")
    })
    abstract OriginalConcertEventOutcomeDto toDto(OriginalConcertEvent originalConcertEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(coverConcertEvent.getOrganization().getId())"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getIdFromEntity(coverConcertEvent.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(coverConcertEvent.getPlaces()))")
    })
    abstract CoverConcertEventOutcomeDto toDto(CoverConcertEvent coverConcertEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(cinemaEvent.getOrganization().getId())"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getIdFromEntity(cinemaEvent.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(cinemaEvent.getPlaces()))")
    })
    abstract CinemaEventOutcomeDto toDto(CinemaEvent cinemaEvent);

    @Mappings({
            @Mapping(target = "organization",
                    expression = "java(theatreEvent.getOrganization().getId())"),
            @Mapping(target = "artists",
                    expression = "java(artistService.getIdFromEntity(theatreEvent.getArtists()))"),
            @Mapping(target = "places",
                    expression = "java(placeService.getIdFromEntity(theatreEvent.getPlaces()))")
    })
    abstract TheatreEventOutcomeDto toDto(TheatreEvent theatreEvent);
}
