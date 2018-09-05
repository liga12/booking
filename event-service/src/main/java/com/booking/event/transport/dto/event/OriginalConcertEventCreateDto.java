package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OriginalConcertEventCreateDto extends AbstractEventCreateDto {

    public OriginalConcertEventCreateDto() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
