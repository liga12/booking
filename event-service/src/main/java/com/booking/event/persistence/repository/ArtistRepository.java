package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    boolean existsByName(String name);

}
