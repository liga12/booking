package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.event.AbstractEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository extends
        JpaRepository<AbstractEvent, Long>,
        JpaSpecificationExecutor<AbstractEvent> {

    boolean existsById(Long id);
}
