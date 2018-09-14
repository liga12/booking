package com.booking.event.transport.dto.event.originalConcert;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventUpdateDto extends AbstractEventUpdateDto {

    public OriginalConcertEventUpdateDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
