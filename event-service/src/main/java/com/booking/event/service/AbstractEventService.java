package com.booking.event.service;

import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;

public interface AbstractEventService {

    Long create(Long organizationId, AbstractEventCreateDto dto);

    AbstractEventOutcomeDto getById(Long id);
}
