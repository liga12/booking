package com.booking.event.dto;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventOutcomeDto extends AbstractEventOutcomeDto {

    public OriginalConcertEventOutcomeDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
