package com.booking.event.dto;


import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventOutcomeDto extends AgeConstrainEventOutcomeDto {

    public CinemaEventOutcomeDto() {
        super(EventType.CINEMA);
    }
}
