package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.entity.place.PlaceStatusType;
import com.booking.event.persistence.entity.place.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaceRepository extends JpaRepository<Place, Long>,
        JpaSpecificationExecutor<Place> {

    boolean existsAllByNumberAndAndRowAndSectionTypeAndEvent(Integer number,
                                                             Integer row,
                                                             SectionType type,
                                                             AbstractEvent event);

    boolean existsByIdAndStatus(Long id, PlaceStatusType type);
}
