package com.booking.event.transport.dto.event.ageConstrain;

import com.booking.event.type.EventType;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class AgeConstrainEventCreateDto extends AbstractEventCreateDto {

    @NotNull
     private Integer minAge;

    public AgeConstrainEventCreateDto(EventType type) {
        super(type);
    }
}
