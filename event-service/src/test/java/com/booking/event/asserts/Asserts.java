package com.booking.event.asserts;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.persistence.entity.event.AbstractEvent;

import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualEventAndDto(List<AbstractEvent> firstDto, List<AbstractEventOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getName(), secondDto.get(i).getName());
            assertEquals(firstDto.get(i).getType(), secondDto.get(i).getType());
            assertEquals(firstDto.get(i).getDescription(), secondDto.get(i).getDescription());
            assertEquals(firstDto.get(i).getDate(), secondDto.get(i).getDate());
            assertEquals(firstDto.get(i).getLocation(), secondDto.get(i).getLocation());
            assertEquals(firstDto.get(i).getPhotoUrl(), secondDto.get(i).getPhotoUrl());
            assertEquals(firstDto.get(i).getOrganization().getId(), secondDto.get(i).getOrganization());
            assertEquals(firstDto.get(i).getArtists(), secondDto.get(i).getArtists());
            assertEquals(firstDto.get(i).getPlaces().size(), secondDto.get(i).getPlaces().size());
        }
    }
}

