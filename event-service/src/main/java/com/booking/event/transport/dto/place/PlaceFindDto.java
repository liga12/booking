package com.booking.event.transport.dto.place;

import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceFindDto {

    private Long id;

    private Double price;

    private Integer row;

    private Integer number;

    private PlaceStatusType status;

    private Long event;

    private SectionType sectionType;
}
