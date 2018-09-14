package com.booking.event.dto;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoverConcertEventOutcomeDto extends AbstractEventOutcomeDto {

    public CoverConcertEventOutcomeDto() {
        super(EventType.COVER_CONCERT);
    }
}
