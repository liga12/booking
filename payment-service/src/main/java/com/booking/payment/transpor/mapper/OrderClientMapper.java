package com.booking.payment.transpor.mapper;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Getter
public abstract class OrderClientMapper {

    PaymentService paymentService;

    @Autowired
    public void setPayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Mappings({
            @Mapping(target = "paymentClient", expression = "java(paymentService.getById(dto.getPaymentClient()))"),
            @Mapping(target = "paymentCustomer", expression = "java(paymentService.getById(dto.getPaymentCustomer()))")
    })
    public abstract OrderClient toEntity(OrderClientCreateDto dto);

    @Mappings({
            @Mapping(target = "paymentClient", expression = "java(orderClient.getPaymentClient().getId())"),
            @Mapping(target = "paymentCustomer", expression = "java(orderClient.getPaymentCustomer().getId())")
    })
    public abstract OrderClientOutcomeDto toDto(OrderClient orderClient);
}
