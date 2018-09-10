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

@Transactional
public interface AbstractEventService {

    @Transactional(readOnly = true)
    Page<AbstractEventOutcomeDto> getAll(AbstractEventFindDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    AbstractEventOutcomeDto getById(Long id);

    @Transactional(readOnly = true)
    Set<AbstractEvent> getById(Set<Long> ids);

    Long create(AbstractEventCreateDto dto);

    Long update(AbstractEventUpdateDto dto);

    void delete(Long id);

    Set<Long> getIdFromEntity(Set<AbstractEvent> abstractEvents);

    void validateEventByActive(Long id);
}
