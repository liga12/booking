package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OriginalConcertEventCreateDto.class, name = "ORIGINAL_CONCERT"),
        @JsonSubTypes.Type(value = CoverConcertEventCreateDto.class, name = "COVER_CONCERT"),
        @JsonSubTypes.Type(value = CinemaEventCreateDto.class, name = "CINEMA"),
        @JsonSubTypes.Type(value = TheatreEventCreateDto.class, name = "THEATRE")
})
public abstract class AbstractEventCreateDto {

    @NotBlank
    private String name;

    private EventType type;

    @NotBlank
    private String description;

    private Long date;

    @NotBlank
    private String location;

    @NotBlank
    private String photoUrl;

    @NotBlank
    private String artists;

    public AbstractEventCreateDto(EventType type) {
        this.type = type;
    }
}
