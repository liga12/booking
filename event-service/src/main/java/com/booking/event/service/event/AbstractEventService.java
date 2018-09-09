package com.booking.event.service.event;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.transport.dto.event.AbstractEventCreateDto;
import com.booking.event.transport.dto.event.AbstractEventFindDto;
import com.booking.event.transport.dto.event.AbstractEventOutcomeDto;
import com.booking.event.transport.dto.event.AbstractEventUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface AbstractEventService {

    @Transactional(readOnly = true)
    Page<AbstractEventOutcomeDto> getAll(AbstractEventFindDto dto, Pageable pageable);

    Long create(Long organizationId, AbstractEventCreateDto dto);

    AbstractEventOutcomeDto getById(Long id);

    Set<AbstractEvent> getById(Set<Long> ids);

    @Transactional
    Long update(Long id, AbstractEventUpdateDto dto);

    @Transactional
    void delete(Long id);

    Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents);
}
