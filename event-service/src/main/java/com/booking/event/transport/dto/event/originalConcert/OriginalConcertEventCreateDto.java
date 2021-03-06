package com.booking.event.transport.dto.event.originalConcert;

import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventCreateDto extends AbstractEventCreateDto {

    public OriginalConcertEventCreateDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
