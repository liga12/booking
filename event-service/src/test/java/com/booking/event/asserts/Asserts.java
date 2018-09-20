package com.booking.event.asserts;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;

import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualEventsAndDtos(List<AbstractEvent> firstDto, List<AbstractEventOutcomeDto> secondDto) {
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

    static void assertEqualDtoAndDto(AbstractEventUpdateDto firstDto, AbstractEventOutcomeDto secondDto) {
        assertEquals(firstDto.getId(), secondDto.getId());
        assertEquals(firstDto.getName(), secondDto.getName());
        assertEquals(firstDto.getType(), secondDto.getType());
        assertEquals(firstDto.getDescription(), secondDto.getDescription());
        assertEquals(firstDto.getDate(), secondDto.getDate());
        assertEquals(firstDto.getLocation(), secondDto.getLocation());
        assertEquals(firstDto.getPhotoUrl(), secondDto.getPhotoUrl());
        assertEquals(firstDto.getOrganization(), secondDto.getOrganization());
        assertEquals(firstDto.getArtists(), secondDto.getArtists());
    }

    static void assertEqualOrganizationsAndDtos(List<Organization> firstDto, List<OrganizationOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getName(), secondDto.get(i).getName());
            assertEquals(firstDto.get(i).getLocation(), secondDto.get(i).getLocation());
            assertEquals(firstDto.get(i).getPhone(), secondDto.get(i).getPhone());
            assertEquals(firstDto.get(i).getEmail(), secondDto.get(i).getEmail());
            assertEquals(firstDto.get(i).getVisible(), secondDto.get(i).getVisible());
            assertEquals(firstDto.get(i).getEvents().size(), secondDto.get(i).getEvents().size());
            assertEquals(firstDto.get(i).getCustomerId(), secondDto.get(i).getCustomerId());
        }
    }

    static void assertEqualDtoAndDto(OrganizationUpdateDto firstDto,OrganizationOutcomeDto secondDto) {
        assertEquals(firstDto.getId(), secondDto.getId());
        assertEquals(firstDto.getName(), secondDto.getName());
        assertEquals(firstDto.getLocation(), secondDto.getLocation());
        assertEquals(firstDto.getPhone(), secondDto.getPhone());
        assertEquals(firstDto.getEmail(), secondDto.getEmail());
    }

    static void assertEqualPlacesAndDtos(List<Place> firstDto, List<PlaceOutcomeDto> secondDto) {
        if (firstDto.size() != secondDto.size()) {
            throw new AssertionError();
        }
        for (int i = 0; i < firstDto.size(); i++) {
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getPrice(), secondDto.get(i).getPrice());
            assertEquals(firstDto.get(i).getNumber(), secondDto.get(i).getNumber());
            assertEquals(firstDto.get(i).getRow(), secondDto.get(i).getRow());
            assertEquals(firstDto.get(i).getStatus(), secondDto.get(i).getStatus());
            assertEquals(firstDto.get(i).getEvent().getId(), secondDto.get(i).getEvent());
            assertEquals(firstDto.get(i).getSectionType(), secondDto.get(i).getSectionType());
        }
    }

    static void assertEqualDtoAndDto(PlaceUpdateDto firstDto, PlaceOutcomeDto secondDto) {
        assertEquals(firstDto.getId(), secondDto.getId());
        assertEquals(firstDto.getPrice(), secondDto.getPrice());
        assertEquals(firstDto.getStatus(), secondDto.getStatus());
    }

}

