package com.booking.payment.transpor.mapper;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderClientMapper {

    public abstract OrderClient toEntity(OrderClientCreateDto dto);
}
