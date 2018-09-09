package com.booking.event.transport.dto.event.ageConstrain.theatre;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.ageConstrain.AgeConstrainEventUpdateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreEventUpdateDto extends AgeConstrainEventUpdateDto {

    public TheatreEventUpdateDto() {
        super(EventType.THEATRE);
    }
}
