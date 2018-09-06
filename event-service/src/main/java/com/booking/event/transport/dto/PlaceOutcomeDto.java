package com.booking.event.transport.dto;

import com.booking.event.persistence.entity.PlaceType;
import com.booking.event.persistence.entity.event.AbstractEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
public class PlaceOutcomeDto {

    private Long id;

    private Integer amount;

    private Double price;

    private Set<Long> abstractEvents;

    private PlaceType type;
}
