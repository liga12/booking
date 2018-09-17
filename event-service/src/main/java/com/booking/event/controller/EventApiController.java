package com.booking.event.controller;

import com.booking.event.api.EventApi;
import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.service.event.EventService;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventApiController implements EventApi {

    private final PlaceService placeService;

    private final EventService eventService;

    private final OrganizationService organizationService;

    @Override
    public boolean existsActivePlace(@PathVariable Long placeId) {
        return placeService.existActivePlace(placeId);
    }

    @Override
    public boolean existsBuyPlace(@PathVariable Long placeId) {
        return placeService.existBuyPlace(placeId);
    }

    @Override
    public double getAmount(@PathVariable Long placeId) {
        return placeService.getById(placeId).getPrice();
    }

    @Override
    public void buyPlace(@PathVariable Long placeId) {
        placeService.buyPlace(placeId);
    }

    @Override
    public PlaceOutcomeDto getPlace(@PathVariable Long placeId) {
        return placeService.getById(placeId);
    }

    @Override
    public boolean existsEvent(@PathVariable Long eventId) {
        return eventService.existById(eventId);
    }

    @Override
    public AbstractEventOutcomeDto getEvent(@PathVariable Long eventId) {
        return eventService.getById(eventId);
    }

    @Override
    public boolean existsOrganization(@PathVariable Long organizationId) {
        return organizationService.exists(organizationId);
    }

    @Override
    public String getOrganizationPhone(@PathVariable Long organizationId) {
        return organizationService.getById(organizationId).getPhone();
    }
}
