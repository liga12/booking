package com.booking.event.controller;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @GetMapping
    public Page<PlaceOutcomeDto> getSchools(PlaceFindDto dto,
                                            @PageableDefault(size = 5) Pageable pageable) {
        return placeService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public PlaceOutcomeDto getById(@PathVariable Long id) {
        return placeService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid PlaceCreateDto dto) {
        return placeService.create(dto);
    }

    @PostMapping
    public Long update(@RequestBody @Valid PlaceUpdateDto dto) {
        return placeService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        placeService.delete(id);
    }
}
