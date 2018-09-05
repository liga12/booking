package com.booking.event.service;

import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;

import java.util.Set;

public interface AbstractEventService {

    Long create(Long organizationId, AbstractEventCreateDto dto, Set<Long> artistIds);

    AbstractEventOutcomeDto getById(Long id);
}
