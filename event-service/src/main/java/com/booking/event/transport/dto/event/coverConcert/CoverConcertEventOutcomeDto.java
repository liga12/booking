package com.booking.event.transport.dto.event.coverConcert;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventOutcomeDto extends AbstractEventOutcomeDto {

    public CoverConcertEventOutcomeDto() {
        super(EventType.COVER_CONCERT);
    }
}
