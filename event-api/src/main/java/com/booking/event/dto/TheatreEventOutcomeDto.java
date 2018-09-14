package com.booking.event.dto;

import com.booking.event.type.EventType;
import com.booking.event.dto.AgeConstrainEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreEventOutcomeDto extends AgeConstrainEventOutcomeDto {

    public TheatreEventOutcomeDto() {
        super(EventType.THEATRE);
    }
}
