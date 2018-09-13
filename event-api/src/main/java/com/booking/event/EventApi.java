package com.booking.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/event-api")
public interface EventApi {

    @GetMapping("/{placeId}")
    boolean existsPlace(@PathVariable("placeId") Long placeId);

    @GetMapping("/getAmount/{placeId}")
    double getAmount(@PathVariable("placeId") Long placeId);

    @PutMapping("/{placeId}")
    void buyPlace(@PathVariable("placeId") Long placeId);
}