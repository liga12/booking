package com.booking.event.persistence.entity.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue(value = EventType.Values.COVER_CONCERT)
public class CoverConcertEvent extends AbstractEvent {

    public CoverConcertEvent() {
        super(EventType.COVER_CONCERT);
    }
}
