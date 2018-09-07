package com.booking.event.persistence.repository;

import com.booking.event.persistence.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

    boolean existsByName(String name);

    boolean existsById(Long id);

    boolean existsByNameAndIdIsNot(String name, Long id);
}
