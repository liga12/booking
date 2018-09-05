package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public abstract class AgeConstrainEventOutcomeDto extends AbstractEventOutcomeDto {

    private Integer minAge;

    public AgeConstrainEventOutcomeDto(EventType type) {
        super(type);
    }
}
