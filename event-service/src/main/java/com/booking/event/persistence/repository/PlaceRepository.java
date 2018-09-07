package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
