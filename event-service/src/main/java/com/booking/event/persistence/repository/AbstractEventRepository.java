package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.event.AbstractEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractEventRepository extends JpaRepository<AbstractEvent, Long> {
}
