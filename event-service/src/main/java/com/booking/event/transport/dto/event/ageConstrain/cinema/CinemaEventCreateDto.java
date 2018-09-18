package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventCreateDto;
import com.booking.event.type.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CinemaEventCreateDto extends AgeConstrainEventCreateDto {

    public CinemaEventCreateDto() {
        super(EventType.CINEMA);
    }
}
