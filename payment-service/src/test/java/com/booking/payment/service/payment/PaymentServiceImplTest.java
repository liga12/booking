package com.booking.payment.service.payment;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.booking.payment.transpor.mapper.PaymentMapper;
import com.google.common.collect.Lists;
import org.assertj.core.util.Sets;
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

import java.util.Set;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void testGetAll() {
        OrderClient client = new OrderClient(2L);
        OrderClient customer = new OrderClient(1L);
        Payment payment = new Payment
                (1L, "aaaaaa", Sets.newLinkedHashSet(client), Sets.newLinkedHashSet(customer));
        PaymentOutcomeDto dto = new PaymentOutcomeDto
                (1L, "aaaaaa", Sets.newLinkedHashSet(client.getId()), Sets.newLinkedHashSet(customer.getId()));
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

    @Test
    public void testGetById() {
        Long id = 1L;
        Payment payment = new Payment(id);
        when(paymentRepository.findById(id)).thenReturn(of(payment));

        Payment result = paymentService.getById(id);

        verify(paymentRepository, times(1)).findById(id);
        assertEquals(payment, result);
    }

    @Test
    public void testCreate() {
        String token = "aaaaaa";
        PaymentCreateDto dto = new PaymentCreateDto(token);
        OrderClient client = new OrderClient(2L);
        OrderClient customer = new OrderClient(1L);
        Payment payment = new Payment
                (1L, token, Sets.newLinkedHashSet(client), Sets.newLinkedHashSet(customer));
        when(paymentRepository.existsByToken(token)).thenReturn(false);
        when(paymentMapper.toEntity(dto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Long result = paymentService.create(dto);

        verify(paymentRepository, times(1)).existsByToken(token);
        verify(paymentMapper, times(1)).toEntity(dto);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result);
    }

    @Test
    public void testExistTokenId(){
        Long id = 1L;
        when(paymentRepository.existsById(id)).thenReturn(true);

        boolean result = paymentService.existTokenId(id);

        verify(paymentRepository, times(1)).existsById(id);
        assertTrue(result);
    }

    @Test
    public void testEntityToId(){
        OrderClient orderClient = new OrderClient(1L);
        OrderClient orderClientSecond = new OrderClient(2L);
        Set<OrderClient> orderClients = Sets.newLinkedHashSet(orderClient, orderClientSecond);

        Set<Long> result = paymentService.entityToId(orderClients);

        assertEquals(Sets.newLinkedHashSet(orderClient.getId(), orderClientSecond.getId()), result);
    }
}