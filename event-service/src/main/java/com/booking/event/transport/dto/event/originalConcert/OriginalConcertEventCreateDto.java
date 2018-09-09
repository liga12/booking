package com.booking.event.transport.dto.event.originalConcert;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventCreateDto extends AbstractEventCreateDto {

    public OriginalConcertEventCreateDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
