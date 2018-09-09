package com.booking.event.transport.dto.event.originalConcert;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventOutcomeDto extends AbstractEventOutcomeDto {

    public OriginalConcertEventOutcomeDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
