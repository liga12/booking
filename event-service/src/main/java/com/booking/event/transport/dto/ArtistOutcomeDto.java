package com.booking.event.transport.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class ArtistOutcomeDto {

    private Long id;

    private String name;

    private Set<Long> events;
}
