package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventCreateDto;
import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventCreateDto extends AgeConstrainEventCreateDto {

    public CinemaEventCreateDto() {
        super(EventType.CINEMA);
    }
}
