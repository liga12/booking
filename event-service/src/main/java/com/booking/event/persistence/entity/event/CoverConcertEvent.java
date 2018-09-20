package com.booking.event.persistence.entity.event;

import com.booking.event.type.EventType;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue(value = EventType.Values.COVER_CONCERT)
public class CoverConcertEvent extends AbstractEvent {

    public CoverConcertEvent() {
        super(EventType.COVER_CONCERT);
    }
}
