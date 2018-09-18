package com.booking.event.transport.dto.place;

import com.booking.event.type.PlaceStatusType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlaceUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private PlaceStatusType status;
}
