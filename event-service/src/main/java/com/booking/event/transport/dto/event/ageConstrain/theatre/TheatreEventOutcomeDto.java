package com.booking.event.transport.dto.event.ageConstrain.theatre;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreEventOutcomeDto extends AgeConstrainEventOutcomeDto {

    public TheatreEventOutcomeDto() {
        super(EventType.THEATRE);
    }
}
