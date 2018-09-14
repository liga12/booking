package com.booking.event.transport.dto.event.ageConstrain.theatre;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreEventCreateDto extends AgeConstrainEventCreateDto {

    public TheatreEventCreateDto() {
        super(EventType.THEATRE);
    }
}
