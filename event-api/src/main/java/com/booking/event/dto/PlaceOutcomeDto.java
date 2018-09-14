package com.booking.event.dto;

import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
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
