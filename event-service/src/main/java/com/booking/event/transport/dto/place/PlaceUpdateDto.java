package com.booking.event.transport.dto.place;

import com.booking.event.type.PlaceStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private PlaceStatusType status;
}
