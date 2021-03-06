package com.booking.event.transport.dto.place;

import com.booking.event.type.SectionType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    public PlaceCreateDto(@NotNull Integer number, @NotNull Integer row, @NotNull Long event, @NotNull SectionType sectionType) {
        this.number = number;
        this.row = row;
        this.event = event;
        this.sectionType = sectionType;
    }
}
