package com.booking.event.transport.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ArtistOutcomeDto {

    private Long id;

    private String name;
}
