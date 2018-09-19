package com.booking.payment.service.payment;

import com.booking.payment.exception.ExistTokenException;
import com.booking.payment.exception.PaymentNotFoundException;
import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.google.common.collect.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.booking.payment.asserts.Asserts.assertEqualPaymentClientAndDto;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PaymentServiceImplIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderClientRepository orderClientRepository;

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PaymentFindDto paymentFindDto = new PaymentFindDto();

        Page<PaymentOutcomeDto> result = paymentService.getAll(paymentFindDto, pageable);

        assertEqualPaymentClientAndDto(paymentRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllById() {
        Long id = 1L;
        Payment payment = paymentRepository.findById(id).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PaymentFindDto paymentFindDto = new PaymentFindDto();
        paymentFindDto.setId(1L);

        Page<PaymentOutcomeDto> result = paymentService.getAll(paymentFindDto, pageable);

        assertEqualPaymentClientAndDto(Lists.newArrayList(payment), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByToken() {
        Long id = 1L;
        Payment payment = paymentRepository.findById(id).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PaymentFindDto paymentFindDto = new PaymentFindDto();
        paymentFindDto.setToken("111110");

        Page<PaymentOutcomeDto> result = paymentService.getAll(paymentFindDto, pageable);

        assertEqualPaymentClientAndDto(Lists.newArrayList(payment), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPayments() {
        Payment payment = paymentRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PaymentFindDto paymentFindDto = new PaymentFindDto();
        paymentFindDto.setClient(Sets.newLinkedHashSet(1L));

        Page<PaymentOutcomeDto> result = paymentService.getAll(paymentFindDto, pageable);

        assertEqualPaymentClientAndDto(Lists.newArrayList(payment), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetById() {
        Long id = 1L;

        Payment result = paymentService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test(expected = PaymentNotFoundException.class)
    @Sql("/scripts/order/init.sql")
    public void testGetByIdWithPaymentNotFoundException() {
        Long id = 100L;

        paymentService.getById(id);
    }

    @Test
    public void testCreate() {
        PaymentCreateDto dto = new PaymentCreateDto("aaaaaa");

        Long result = paymentService.create(dto);

        paymentRepository.findById(result).orElseThrow(AssertionError::new);
    }

    @Test(expected = ExistTokenException.class)
    public void testCreateWithExistTokenException() {
        Payment payment = new Payment("aaaaaa");
        paymentRepository.save(payment);
        PaymentCreateDto dto = new PaymentCreateDto("aaaaaa");

        paymentService.create(dto);
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testExistTokenId() {

        assertTrue(paymentService.existTokenId(1L));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testExistTokenIdFalse() {

        assertFalse(paymentService.existTokenId(100L));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void entityToId() {
        List<OrderClient> all = orderClientRepository.findAll();
        Set<OrderClient> orders = Sets.newHashSet(all);

        Set<Long> result = paymentService.entityToId(orders);

        assertEquals(orders.size(), result.size());
        Set<Long> ids = new HashSet<>();
        for (OrderClient client : orders) {
            ids.add(client.getId());
        }
        assertEquals(ids,result);
    }
}
