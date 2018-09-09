package com.booking.event.transport.dto.event.ageConstrain;

import com.booking.event.persistence.entity.event.EventType;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class AgeConstrainEventUpdateDto extends AbstractEventUpdateDto {

    @NotNull
    private Integer minAge;

    public AgeConstrainEventUpdateDto(EventType type) {
        super(type);
    }
}
