package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventUpdateDto;
import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventUpdateDto extends AgeConstrainEventUpdateDto {

    public CinemaEventUpdateDto() {
        super(EventType.CINEMA);
    }
}
