package com.booking.event.transport.dto.event.ageConstrain.cinema;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventCreateDto extends AgeConstrainEventCreateDto {

    public CinemaEventCreateDto() {
        super(EventType.CINEMA);
    }
}