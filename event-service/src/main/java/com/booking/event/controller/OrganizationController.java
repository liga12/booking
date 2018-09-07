package com.booking.event.controller;

import com.booking.event.service.organization.OrganizationService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public Page<OrganizationOutcomeDto> getSchools(OrganizationFindDto dto,
                                                   @PageableDefault(size = 5) Pageable pageable) {
        return organizationService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public OrganizationOutcomeDto getById(@PathVariable Long id) {
        return organizationService.getById(id);
    }

    @PutMapping
    public Long create(@RequestBody @Valid OrganizationCreateDto dto) {
        return organizationService.create(dto);
    }

    @PostMapping
    public Long update(@RequestBody @Valid OrganizationUpdateDto dto) {
        return organizationService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
