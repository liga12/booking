package com.booking.event.controller;

import com.booking.event.service.event.EventService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    @Getter
    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Page<AbstractEventOutcomeDto> getSchools(EventFindDto dto,
                                                    @PageableDefault(size = 5) Pageable pageable) {
        return eventService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public AbstractEventOutcomeDto getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid AbstractEventCreateDto dto) {
        return eventService.create(dto);
    }

    @PostMapping
    public Long update(@RequestBody @Valid AbstractEventUpdateDto dto) {
        return eventService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}
