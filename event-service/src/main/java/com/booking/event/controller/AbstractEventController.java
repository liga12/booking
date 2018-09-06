package com.booking.event.controller;

import com.booking.event.service.AbstractEventService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class AbstractEventController {

    @Getter
    private AbstractEventService abstractEventService;

    @Autowired
    public void setAbstractEventService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @GetMapping("/{id}")
    public AbstractEventOutcomeDto getById(@PathVariable Long id) {
        return abstractEventService.getById(id);
    }

    @PutMapping("/{organizationId}")
    public Long create(@PathVariable Long organizationId,
                       @RequestBody @Valid AbstractEventCreateDto dto) {
        return abstractEventService.create(organizationId, dto);
    }
}
