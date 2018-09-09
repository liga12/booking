package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventUpdateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventUpdateDto extends AgeConstrainEventUpdateDto {

    public CinemaEventUpdateDto() {
        super(EventType.CINEMA);
    }
}
