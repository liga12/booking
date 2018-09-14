package com.booking.payment.service.order;

import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderClientService {

    @Transactional(readOnly = true)
    Page<OrderClientOutcomeDto> getAll(OrderClientFindDto dto, Pageable pageable);

    Long create(OrderClientCreateDto dto);
}
