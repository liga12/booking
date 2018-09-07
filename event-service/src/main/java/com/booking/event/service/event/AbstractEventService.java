package com.booking.event.service.event;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;

import java.util.Set;

public interface AbstractEventService {

    Long create(Long organizationId, AbstractEventCreateDto dto);

    AbstractEventOutcomeDto getById(Long id);

    Set<AbstractEvent> getById(Set<Long> ids);

    Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents);
}
