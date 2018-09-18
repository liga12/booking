package com.booking.event.dto;

import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOutcomeDto {

    private Long id;

    private Double price;

    private Integer number;

    private Integer row;

    private PlaceStatusType status;

    private Long event;

    private SectionType sectionType;

    public PlaceOutcomeDto(Integer number, Integer row, Long event) {
        this.number = number;
        this.row = row;
        this.event = event;
    }
}
