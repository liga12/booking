package com.booking.payment.service.order;

import com.booking.payment.transpor.dto.OrderClientCreateDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderClientService {

    Long create(OrderClientCreateDto dto);
}
