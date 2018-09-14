package com.booking.event.transport.dto.event.coverConcert;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventCreateDto extends AbstractEventCreateDto {

    public CoverConcertEventCreateDto() {
        super(EventType.COVER_CONCERT);
    }
}
