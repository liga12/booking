package com.booking.event.persistence.entity.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = EventType.Values.THEATRE)
public class TheatreEvent extends AgeConstrainEvent {

    public TheatreEvent() {
        super(EventType.THEATRE);
    }
}
