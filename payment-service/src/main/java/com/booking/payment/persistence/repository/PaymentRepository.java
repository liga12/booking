package com.booking.payment.persistence.repository;

import com.booking.payment.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends
        JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {

    boolean existsByToken(String token);

    boolean existsById(Long id);
}
