package com.booking.payment.transpor.mapper;

import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Getter
public abstract class PaymentMapper {

    PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;

    }

    public abstract Payment toEntity(PaymentCreateDto dto);

    @Mappings({
            @Mapping(target = "client", expression = "java(paymentService.entityToId(payment.getClient()))"),
            @Mapping(target = "customer", expression = "java(paymentService.entityToId(payment.getCustomer()))")
    })
    public abstract PaymentOutcomeDto toDto(Payment payment);
}
