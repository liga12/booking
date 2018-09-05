package com.booking.event.controller;

import com.booking.event.service.AbstractEventService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class AbstractEventController {

    private final AbstractEventService abstractEventService;

    @GetMapping("/{id}")
    public AbstractEventOutcomeDto getById(@PathVariable Long id) {
        return abstractEventService.getById(id);
    }

    @PutMapping("/{organizationId}")
    public Long create(@PathVariable Long organizationId,
                       @RequestParam Set<Long> artistId,
                       @RequestBody @Valid AbstractEventCreateDto dto) {
        return abstractEventService.create(organizationId, dto, artistId);
    }
}
