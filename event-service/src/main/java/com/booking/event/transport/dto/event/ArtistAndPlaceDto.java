package com.booking.event.transport.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ArtistAndPlaceDto {

    private Set<Long> artistIds;
}
