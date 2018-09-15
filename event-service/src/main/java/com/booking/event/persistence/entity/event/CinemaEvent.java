package com.booking.event.persistence.entity.event;

import com.booking.event.type.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue(value = EventType.Values.CINEMA)
public class CinemaEvent extends AgeConstrainEvent {

    public CinemaEvent() {
        super(EventType.CINEMA);
    }
}
