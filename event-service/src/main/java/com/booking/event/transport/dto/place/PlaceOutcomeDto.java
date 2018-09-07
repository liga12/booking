package com.booking.event.transport.dto.place;

import com.booking.event.persistence.entity.place.PlaceRowType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PlaceOutcomeDto {

    private Long id;

    private Integer amount;

    private Double price;

    private Set<Long> abstractEvents;

    private PlaceRowType type;
}
