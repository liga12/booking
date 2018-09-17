package com.booking.payment.service.payment;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.booking.payment.transpor.mapper.PaymentMapper;
import com.google.common.collect.Lists;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {

    @InjectMocks
    @Spy
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentRepository paymentRepository;

    @Bean
    public PaymentServiceImpl getPaymentService() {
        return new PaymentServiceImpl();
    }

    @Test
    public void testGetAll() {
        OrderClient client = OrderClient.builder().id(1L).build();
        OrderClient customer = OrderClient.builder().id(1L).build();
        Payment payment = Payment.builder()
                .id(1L)
                .token("aaaaaa")
                .client(Sets.newLinkedHashSet(client))
                .customer(Sets.newLinkedHashSet(customer))
                .build();
        PaymentOutcomeDto dto = PaymentOutcomeDto.builder()
                .id(1L)
                .token("aaaaaa")
                .client(Sets.newLinkedHashSet(client.getId()))
                .customer(Sets.newLinkedHashSet(customer.getId()))
                .build();
        PageImpl<Payment> page = new PageImpl(Lists.newArrayList(payment));
        when(paymentRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
                )
        ).thenReturn(page);
        when(paymentMapper.toDto(payment)).thenReturn(dto);

        Page<PaymentOutcomeDto> result = paymentService.getAll(
                new PaymentFindDto(),
                PageRequest.of(0, 100)
        );

        verify(paymentMapper, times(1)).toDto(payment);
        assertEquals(page.map(paymentMapper::toDto), result);
    }

}