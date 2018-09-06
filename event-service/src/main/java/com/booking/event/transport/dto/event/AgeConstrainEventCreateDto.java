package com.booking.event.transport.dto.event;

import com.booking.event.persistence.entity.event.EventType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public abstract class AgeConstrainEventCreateDto extends AbstractEventCreateDto {

    @NotNull
     private Integer minAge;

    public AgeConstrainEventCreateDto(EventType type) {
        super(type);
    }
}
