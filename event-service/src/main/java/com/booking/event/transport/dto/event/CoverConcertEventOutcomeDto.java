package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventOutcomeDto extends AbstractEventOutcomeDto {

    public CoverConcertEventOutcomeDto() {
        super(EventType.COVER_CONCERT);
    }
}
