package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByName(String name);
}
