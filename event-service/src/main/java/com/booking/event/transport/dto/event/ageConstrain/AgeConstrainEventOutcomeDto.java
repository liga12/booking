package com.booking.event.transport.dto.event.ageConstrain;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
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
