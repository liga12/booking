package com.booking.event.api;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.dto.PlaceOutcomeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/event-api")
public interface EventApi {

    @GetMapping("/existActive/{placeId}")
    boolean existsActivePlace(@PathVariable("placeId") Long placeId);

    @GetMapping("/existBuy/{placeId}")
    boolean existsBuyPlace(@PathVariable("placeId") Long placeId);

    @GetMapping("/getPlace/{placeId}")
    PlaceOutcomeDto getPlace(@PathVariable("placeId") Long placeId);

    @GetMapping("/getAmount/{placeId}")
    double getAmount(@PathVariable("placeId") Long placeId);

    @PutMapping("/{placeId}")
    void buyPlace(@PathVariable("placeId") Long placeId);

    @GetMapping("/existsEvent/{eventId}")
    boolean existsEvent(@PathVariable("eventId") Long eventId);

    @GetMapping("/getEvent/{eventId}")
    AbstractEventOutcomeDto getEvent(@PathVariable("eventId") Long eventId);

    @GetMapping("/existsOrganization/{organizationId}")
    boolean existsOrganization(@PathVariable("organizationId") Long organizationId);

    @GetMapping("/getOrganizationPhone/{organizationId}")
    String getOrganizationPhone(@PathVariable("organizationId") Long organizationId);
}