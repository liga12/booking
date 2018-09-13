package com.booking.payment.service;

import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.mapper.OrderClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClientRepository orderClientRepository;

    private final OrderClientMapper;

    @Override
    public Long create(OrderClientCreateDto){

    }
}
