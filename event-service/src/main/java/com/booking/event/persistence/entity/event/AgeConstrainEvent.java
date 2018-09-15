package com.booking.event.persistence.entity.event;

import com.booking.event.type.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
abstract class AgeConstrainEvent extends AbstractEvent {

    @Column(nullable = false)
    private Integer minAge;

    AgeConstrainEvent(EventType type) {
        super(type);
    }
}
