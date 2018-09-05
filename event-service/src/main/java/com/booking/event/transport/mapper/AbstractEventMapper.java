package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.event.*;
import com.booking.event.transport.dto.event.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
        OrganizationMapper.class,
        ArtistMapper.class})
public interface AbstractEventMapper {

    default AbstractEvent toEntity(AbstractEventCreateDto dto) {
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

    default AbstractEvent toEntity(AbstractEventOutcomeDto dto) {
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

    default AbstractEventOutcomeDto toDto(AbstractEvent abstractEvent) {
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

    OriginalConcertEvent toEntity(OriginalConcertEventCreateDto dto);

    CoverConcertEvent toEntity(CoverConcertEventCreateDto dto);

    CinemaEvent toEntity(CinemaEventCreateDto dto);

    TheatreEvent toEntity(TheatreEventCreateDto dto);

    OriginalConcertEvent toEntity(OriginalConcertEventOutcomeDto dto);

    CoverConcertEvent toEntity(CoverConcertEventOutcomeDto dto);

    CinemaEvent toEntity(CinemaEventOutcomeDto dto);

    TheatreEvent toEntity(TheatreEventOutcomeDto dto);

    OriginalConcertEventOutcomeDto toDto(OriginalConcertEvent originalConcertEvent);

    CoverConcertEventOutcomeDto toDto(CoverConcertEvent coverConcertEvent);

    CinemaEventOutcomeDto toDto(CinemaEvent cinemaEvent);

    TheatreEventOutcomeDto toDto(TheatreEvent theatreEvent);
}
