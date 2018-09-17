package com.booking.payment.service.order;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.entity.Payment;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.service.feign.EventService;
import com.booking.payment.service.feign.UserService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import com.booking.payment.transpor.mapper.OrderClientMapper;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderClientServiceImplTest {

    @Mock
    private OrderClientRepository orderClientRepository;

    @Mock
    private OrderClientMapper orderClientMapper;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Spy
    @InjectMocks
    private OrderClientServiceImpl orderClientService;

    @Test
    public void testGetAll() {
        Payment client = Payment.builder()
                .id(1L)
                .build();
        Payment customer = Payment.builder()
                .id(2L)
                .build();
        OrderClient orderClient = OrderClient.builder()
                .id(1L)
                .amount(10d)
                .paymentClient(client)
                .paymentCustomer(customer)
                .placeId(1L)
                .build();
        OrderClientOutcomeDto dto = OrderClientOutcomeDto.builder()
                .id(1L)
                .amount(10d)
                .paymentClient(client.getId())
                .paymentCustomer(customer.getId())
                .placeId(1L)
                .build();
        PageImpl<OrderClient> page = new PageImpl(Lists.newArrayList(orderClient));
        when(orderClientRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
                )
        ).thenReturn(page);
        when(orderClientMapper.toDto(orderClient)).thenReturn(dto);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(
                new OrderClientFindDto(),
                PageRequest.of(0, 100)
        );

        verify(orderClientRepository, times(1)).findAll(
                any(Specification.class),
                any(Pageable.class)
        );
        verify(orderClientMapper, times(1)).toDto(orderClient);
        assertEquals(page.map(orderClientMapper::toDto), result);
    }

    @Test
    public void testCreate() {
        Payment client = Payment.builder()
                .id(1L)
                .build();
        Payment customer = Payment.builder()
                .id(2L)
                .build();
        OrderClient orderClient = OrderClient.builder()
                .id(1L)
                .amount(10d)
                .paymentClient(client)
                .paymentCustomer(customer)
                .placeId(1L)
                .build();
        OrderClientCreateDto orderClientCreateDto = OrderClientCreateDto.builder()
                .paymentClient(client.getId())
                .paymentCustomer(customer.getId())
                .placeId(1L)
                .build();
        when(eventService.existsActivePlace(orderClientCreateDto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(orderClientCreateDto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(orderClientCreateDto.getPaymentClient())).thenReturn(true);
        when(orderClientMapper.toEntity(orderClientCreateDto)).thenReturn(orderClient);
        when(eventService.getAmount(orderClientCreateDto.getPlaceId())).thenReturn(10d);
        doNothing().when(eventService).buyPlace(orderClientCreateDto.getPlaceId());
        when(orderClientRepository.save(orderClient)).thenReturn(orderClient);

        Long result = orderClientService.create(orderClientCreateDto);

        verify(eventService, times(1)).existsActivePlace(orderClientCreateDto.getPlaceId());
        verify(userService, times(1)).existCustomerByPaymentId(orderClientCreateDto.getPaymentCustomer());
        verify(userService, times(1)).existClientByPaymentId(orderClientCreateDto.getPaymentClient());
        verify(orderClientMapper, times(1)).toEntity(orderClientCreateDto);
        verify(eventService, times(1)).getAmount(orderClientCreateDto.getPlaceId());
        verify(eventService, times(1)).buyPlace(orderClientCreateDto.getPlaceId());
        verify(orderClientRepository, times(1)).save(orderClient);
        assertEquals(orderClient.getId(),result);
    }

}