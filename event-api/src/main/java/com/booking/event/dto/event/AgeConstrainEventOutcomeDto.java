package com.booking.event.dto.event;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class AgeConstrainEventOutcomeDto extends AbstractEventOutcomeDto {

    private Integer minAge;

    AgeConstrainEventOutcomeDto(EventType type) {
        super(type);
    }
}
