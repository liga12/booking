package com.booking.event.transport.dto.event;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import com.booking.event.transport.dto.event.ageConstrain.theatre.TheatreEventUpdateDto;
import com.booking.event.transport.dto.event.coverConcert.CoverConcertEventUpdateDto;
import com.booking.event.dto.OriginalConcertEventOutcomeDto;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "rowType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OriginalConcertEventOutcomeDto.class, name = "ORIGINAL_CONCERT"),
        @JsonSubTypes.Type(value = CoverConcertEventUpdateDto.class, name = "COVER_CONCERT"),
        @JsonSubTypes.Type(value = CinemaEventUpdateDto.class, name = "CINEMA"),
        @JsonSubTypes.Type(value = TheatreEventUpdateDto.class, name = "THEATRE")
})
public abstract class AbstractEventUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private EventType type;

    @NotBlank
    private String description;

    @NotNull
    private Long date;

    @NotBlank
    private String location;

    @NotBlank
    private String photoUrl;

    @NotNull
    private Long organization;

    @NotBlank
    private String artists;

    public AbstractEventUpdateDto(EventType type) {
        this.type = type;
    }
}
