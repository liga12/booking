package com.booking.payment.transpor.mapper;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderClientMapperTest {

    @InjectMocks
    private OrderClientMapperImpl orderClientMapper;

    @Mock
    private PaymentService paymentService;

    @Test
    public void toEntity() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 2L, 3L);
        Payment customerPayment = new Payment(3L);
        Payment clientPayment = new Payment(2L);
        when(paymentService.getById(dto.getPaymentCustomer())).thenReturn(customerPayment);
        when(paymentService.getById(dto.getPaymentClient())).thenReturn(clientPayment);

        OrderClient result = orderClientMapper.toEntity(dto);

        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getPaymentClient(), result.getPaymentClient().getId());
        assertEquals(dto.getPaymentCustomer(), result.getPaymentCustomer().getId());
    }

    @Test
    public void toDto() {
        Payment customerPayment = new Payment(3L);
        Payment clientPayment = new Payment(2L);
        OrderClient orderClient = new OrderClient(
                1L,
                2L,
                clientPayment,
                customerPayment,
                10D
        );

        OrderClientOutcomeDto result = orderClientMapper.toDto(orderClient);

        assertEquals(orderClient.getId(), result.getId());
        assertEquals(orderClient.getPlaceId(), result.getPlaceId());
        assertEquals(orderClient.getPaymentClient().getId(), result.getPaymentClient());
        assertEquals(orderClient.getPaymentCustomer().getId(), result.getPaymentCustomer());
        assertEquals(orderClient.getAmount(), result.getAmount());
    }


}