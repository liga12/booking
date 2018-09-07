package com.booking.event.controller;

import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceOutcomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @GetMapping("/{id}")
    public PlaceOutcomeDto getById(@PathVariable Long id){
        return placeService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid PlaceCreateDto dto){
        return placeService.create(dto);
    }
}
