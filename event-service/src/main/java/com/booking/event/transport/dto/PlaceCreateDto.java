package com.booking.event.transport.dto;

import com.booking.event.persistence.entity.PlaceType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PlaceCreateDto {

    @NotNull
    private Integer amount;

    @NotNull
    private Double price;

    private PlaceType type;
}
