package com.booking.event.transport.dto.place;

import com.booking.event.persistence.entity.place.PlaceRowType;
import com.booking.event.persistence.entity.place.PlaceStatusType;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class PlaceCreateDto {

    @NotNull
    private Integer amount;

    @NotNull
    private Double price;

    @NotNull
    private Set<AbstractEventOutcomeDto> eventOutcomeDtoSet;

    @NotNull
    private PlaceRowType rowType;

    @NotNull
    private PlaceStatusType status;
}
