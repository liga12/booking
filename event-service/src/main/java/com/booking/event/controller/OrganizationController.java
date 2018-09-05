package com.booking.event.controller;

import com.booking.event.service.OrganizationService;
import com.booking.event.transport.dto.OrganizationCreateDto;
import com.booking.event.transport.dto.OrganizationOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{id}")
    public OrganizationOutcomeDto getById(@PathVariable Long id) {
        return organizationService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid OrganizationCreateDto dto) {
        return organizationService.create(dto);
    }
}
