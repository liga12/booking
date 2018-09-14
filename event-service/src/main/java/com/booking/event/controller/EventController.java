package com.booking.event.controller;

import com.booking.event.service.event.AbstractEventService;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventFindDto;
import com.booking.event.dto.AbstractEventOutcomeDto;
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
    private AbstractEventService abstractEventService;

    @Autowired
    public void setAbstractEventService(AbstractEventService abstractEventService) {
        this.abstractEventService = abstractEventService;
    }

    @GetMapping
    public Page<AbstractEventOutcomeDto> getSchools(AbstractEventFindDto dto,
                                                    @PageableDefault(size = 5) Pageable pageable) {
        return abstractEventService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public AbstractEventOutcomeDto getById(@PathVariable Long id) {
        return abstractEventService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid AbstractEventCreateDto dto) {
        return abstractEventService.create(dto);
    }

    @PostMapping
    public Long update(@RequestBody @Valid AbstractEventUpdateDto dto) {
        return abstractEventService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        abstractEventService.delete(id);
    }
}
