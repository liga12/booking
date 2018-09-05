package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventCreateDto extends AbstractEventCreateDto {

    public CoverConcertEventCreateDto() {
        super(EventType.COVER_CONCERT);
    }
}
