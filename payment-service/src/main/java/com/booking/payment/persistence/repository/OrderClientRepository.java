package com.booking.payment.persistence.repository;

import com.booking.payment.persistence.entity.OrderClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderClientRepository extends
        JpaRepository<OrderClient, Long>,
        JpaSpecificationExecutor<OrderClient> {
}
