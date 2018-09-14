package com.booking.event.persistence.entity.event;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public abstract class AgeConstrainEvent extends AbstractEvent {

    @Column(nullable = false)
    private Integer minAge;

    public AgeConstrainEvent(EventType type) {
        super(type);
    }
}
