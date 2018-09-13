package com.booking.payment.service.payment;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface PaymentService {

    @Transactional(readOnly = true)
    Page<PaymentOutcomeDto> getAll(PaymentFindDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    Payment getById(Long id);

    Long create(PaymentCreateDto dto);

    @Transactional(readOnly = true)
    boolean existTokenId(Long tokenId);

    @Transactional(readOnly = true)
    void validateById(Long id);

    Set<Long> entityToId(Set<OrderClient> clients);
}
