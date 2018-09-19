package com.booking.payment.service.order;

import com.booking.payment.exception.ClientNotFoundException;
import com.booking.payment.exception.CustomerNotFoundException;
import com.booking.payment.exception.PlaceNotFoundException;
import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.service.feign.EventService;
import com.booking.payment.service.feign.UserService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.booking.payment.asserts.Asserts.assertEqualOrderClientAndDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderClientServiceImplIntegrationTest {

    @Autowired
    private OrderClientService orderClientService;

    @Autowired
    private OrderClientRepository orderClientRepository;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserService userService;

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto order = new OrderClientFindDto();

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(order, pageable);

        assertEqualOrderClientAndDto(orderClientRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllById() {
        Long id = 1L;
        OrderClient order = orderClientRepository.findById(id).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setId(id);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPlaceId() {
        OrderClient order = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setPlaceId(1L);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPaymentClient() {
        OrderClient order = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);
        OrderClient order2 = orderClientRepository.findById(2L).orElseThrow(AssertionError::new);
        OrderClient order3 = orderClientRepository.findById(10L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setPaymentClient(1L);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order, order2, order3), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPaymentCustomer() {
        OrderClient order = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);
        OrderClient order2 = orderClientRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setPaymentCustomer(1L);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order, order2), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAmount() {
        OrderClient order = orderClientRepository.findById(4L).orElseThrow(AssertionError::new);
        OrderClient order2 = orderClientRepository.findById(6L).orElseThrow(AssertionError::new);
        OrderClient order3 = orderClientRepository.findById(9L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setAmount(4.1);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order, order2, order3), result.getContent());
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAll() {
        OrderClient order = orderClientRepository.findById(6L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrderClientFindDto schoolFindDto = new OrderClientFindDto();
        schoolFindDto.setId(6L);
        schoolFindDto.setAmount(4.1);
        schoolFindDto.setPaymentCustomer(4L);
        schoolFindDto.setPaymentClient(7L);

        Page<OrderClientOutcomeDto> result = orderClientService.getAll(schoolFindDto, pageable);

        assertEqualOrderClientAndDto(Lists.newArrayList(order), result.getContent());
    }

    @Test
    @Sql("/scripts/order/create.sql")
    public void testCreate() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 1L, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(dto.getPaymentClient())).thenReturn(true);
        when(eventService.getAmount(dto.getPlaceId())).thenReturn(10D);
        doNothing().when(eventService).buyPlace(dto.getPlaceId());

        Long result = orderClientService.create(dto);

        OrderClient order = orderClientRepository.findById(result).orElseThrow(AssertionError::new);
        assertEquals(order.getId(), result);
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithPlaceNotFoundException() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 1L, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(false);

        orderClientService.create(dto);
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithPlaceIdNull() {
        OrderClientCreateDto dto = new OrderClientCreateDto(null, 1L, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);

        orderClientService.create(dto);
    }

    @Test(expected = CustomerNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithCustomerNotFoundException() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 1L, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(false);

        orderClientService.create(dto);
    }

    @Test(expected = CustomerNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithPaymentClientNull() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 1L, null);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);

        orderClientService.create(dto);
    }

    @Test(expected = ClientNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithClientNotFoundException() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, 1L, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(dto.getPaymentClient())).thenReturn(false);

        orderClientService.create(dto);
    }

    @Test(expected = ClientNotFoundException.class)
    @Sql("/scripts/order/create.sql")
    public void testCreateWithPaymentCustomerNull() {
        OrderClientCreateDto dto = new OrderClientCreateDto(1L, null, 2L);
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(dto.getPaymentClient())).thenReturn(true);

        orderClientService.create(dto);
    }
}