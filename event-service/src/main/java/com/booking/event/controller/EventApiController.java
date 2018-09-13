package com.booking.event.controller;

import com.booking.event.EventApi;
import com.booking.event.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventApiController implements EventApi {

    private final PlaceService placeService;

    @Override
    public boolean existsPlace(@PathVariable Long placeId) {
        return placeService.existPlace(placeId);
    }

    @Override
    public double getAmount(@PathVariable Long placeId) {
        return placeService.getById(placeId).getPrice();
    }

    @Override
    public void buyPlace(@PathVariable Long placeId) {
        placeService.buyPlace(placeId);
    }
}
