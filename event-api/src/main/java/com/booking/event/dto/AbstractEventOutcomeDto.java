package com.booking.event.dto;

import com.booking.event.type.EventType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "rowType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OriginalConcertEventOutcomeDto.class, name = "ORIGINAL_CONCERT"),
        @JsonSubTypes.Type(value = CoverConcertEventOutcomeDto.class, name = "COVER_CONCERT"),
        @JsonSubTypes.Type(value = CinemaEventOutcomeDto.class, name = "CINEMA"),
        @JsonSubTypes.Type(value = TheatreEventOutcomeDto.class, name = "THEATRE")
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
