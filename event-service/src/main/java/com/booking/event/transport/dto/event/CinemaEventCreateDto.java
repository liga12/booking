package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CinemaEventCreateDto extends AgeConstrainEventCreateDto {

    public CinemaEventCreateDto() {
        super(EventType.CINEMA);
    }
}
