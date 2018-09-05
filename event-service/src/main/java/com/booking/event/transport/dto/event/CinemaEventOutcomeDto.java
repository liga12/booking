package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventOutcomeDto extends AgeConstrainEventOutcomeDto {

    public CinemaEventOutcomeDto() {
        super(EventType.CINEMA);
    }
}
