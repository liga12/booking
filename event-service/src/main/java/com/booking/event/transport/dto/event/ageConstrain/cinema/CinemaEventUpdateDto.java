package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventUpdateDto;
import com.booking.event.type.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CinemaEventUpdateDto extends AgeConstrainEventUpdateDto {

    public CinemaEventUpdateDto() {
        super(EventType.CINEMA);
    }
}
