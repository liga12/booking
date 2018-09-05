package com.booking.event.persistence.entity.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = EventType.Values.ORIGINAL_CONCERT)
public class OriginalConcertEvent extends AbstractEvent {

    public OriginalConcertEvent() {
        super(EventType.ORIGINAL_CONCERT);
    }
}
