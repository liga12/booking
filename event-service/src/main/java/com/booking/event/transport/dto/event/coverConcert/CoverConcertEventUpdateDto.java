package com.booking.event.transport.dto.event.coverConcert;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventUpdateDto extends AbstractEventUpdateDto {

    public CoverConcertEventUpdateDto() {
        super(EventType.COVER_CONCERT);
    }
}
