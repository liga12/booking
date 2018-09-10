package com.booking.event.transport.dto.place;

import com.booking.event.persistence.entity.place.SectionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlaceCreateDto {

    @NotNull
    private Double price;

    @NotNull
    private Integer number;

    @NotNull
    private Integer row;

    @NotNull
    private Long event;

    @NotNull
    private SectionType sectionType;
}
