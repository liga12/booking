package com.booking.event.dto;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AgeConstrainEventOutcomeDto extends AbstractEventOutcomeDto {

    private Integer minAge;

    public AgeConstrainEventOutcomeDto(EventType type) {
        super(type);
    }
}
