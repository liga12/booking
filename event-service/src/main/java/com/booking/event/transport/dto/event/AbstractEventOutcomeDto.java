package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.theatre.TheatreEventCreateDto;
import com.booking.event.transport.dto.event.coverConcert.CoverConcertEventCreateDto;
import com.booking.event.transport.dto.event.originalConcert.OriginalConcertEventCreateDto;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "rowType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OriginalConcertEventCreateDto.class, name = "ORIGINAL_CONCERT"),
        @JsonSubTypes.Type(value = CoverConcertEventCreateDto.class, name = "COVER_CONCERT"),
        @JsonSubTypes.Type(value = CinemaEventCreateDto.class, name = "CINEMA"),
        @JsonSubTypes.Type(value = TheatreEventCreateDto.class, name = "THEATRE")
})
public abstract class AbstractEventOutcomeDto {

    private Long id;

    private String name;

    private EventType type;

    private String description;

    private Long date;

    private String location;

    private String photoUrl;

    private Long organization;

    private String artists;

    private Set<Long> places;

    private Boolean visible;

    public AbstractEventOutcomeDto(EventType type) {
        this.type = type;
    }
}
