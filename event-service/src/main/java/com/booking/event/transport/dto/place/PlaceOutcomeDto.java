package com.booking.event.transport.dto.place;

import com.booking.event.persistence.entity.place.SectionType;
import com.booking.event.persistence.entity.place.PlaceStatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOutcomeDto {

    private Long id;

    private Double price;

    private Integer number;

    private Integer row;

    private PlaceStatusType status;

    private Long event;

    private SectionType sectionType;
}
