package com.booking.event.controller.handler;

import com.booking.event.service.ArtistService;
import com.booking.event.transport.dto.ArtistCreateDto;
import com.booking.event.transport.dto.ArtistOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ArtistOutcomeDto getById(@PathVariable Long id) {
        return artistService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody ArtistCreateDto dto) {
        return artistService.create(dto);
    }

}
