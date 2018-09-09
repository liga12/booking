package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventOutcomeDto extends AgeConstrainEventOutcomeDto {

    public CinemaEventOutcomeDto() {
        super(EventType.CINEMA);
    }
}
