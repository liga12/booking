package com.booking.payment.controller;

import com.booking.payment.persistence.entity.OrderClient;
import com.booking.payment.persistence.repository.OrderClientRepository;
import com.booking.payment.service.feign.EventService;
import com.booking.payment.service.feign.UserService;
import com.booking.payment.service.order.OrderClientService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.booking.payment.util.Converter.mapToJson;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderClientService orderClientService;

    @Autowired
    private OrderClientRepository orderClientRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private EventService eventService;

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAll() throws Exception {
        List<OrderClientOutcomeDto> orders = orderClientService.getAll(
                new OrderClientFindDto(),
                PageRequest.of(0, 2)
        ).getContent();

        mockMvc.perform(get("/orders")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.get(0).getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.get(0).getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.get(0).getPaymentClient()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.get(0).getPaymentCustomer()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.get(0).getAmount()))
                .andExpect(jsonPath("$.content[1].id").value(orders.get(1).getId()))
                .andExpect(jsonPath("$.content[1].placeId").value(orders.get(1).getPlaceId()))
                .andExpect(jsonPath("$.content[1].paymentClient").value(orders.get(1).getPaymentClient()))
                .andExpect(jsonPath("$.content[1].paymentCustomer").value(orders.get(1).getPaymentCustomer()))
                .andExpect(jsonPath("$.content[1].amount").value(orders.get(1).getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllById() throws Exception {
        OrderClient orders = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPlaceId() throws Exception {
        OrderClient orders = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?placeId=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPaymentClient() throws Exception {
        OrderClient orders = orderClientRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?paymentClient=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByPaymentCustomer() throws Exception {
        OrderClient orders = orderClientRepository.findById(8L).orElseThrow(AssertionError::new);
        OrderClient orders2 = orderClientRepository.findById(10L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?paymentCustomer=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.getAmount()))
                .andExpect(jsonPath("$.content[1].id").value(orders2.getId()))
                .andExpect(jsonPath("$.content[1].placeId").value(orders2.getPlaceId()))
                .andExpect(jsonPath("$.content[1].paymentClient").value(orders2.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[1].paymentCustomer").value(orders2.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[1].amount").value(orders2.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAmount() throws Exception {
        OrderClient orders = orderClientRepository.findById(1L).orElseThrow(AssertionError::new);
        OrderClient orders2 = orderClientRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?amount=10")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[0].amount").value(orders.getAmount()))
                .andExpect(jsonPath("$.content[1].id").value(orders2.getId()))
                .andExpect(jsonPath("$.content[1].placeId").value(orders2.getPlaceId()))
                .andExpect(jsonPath("$.content[1].paymentClient").value(orders2.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[1].paymentCustomer").value(orders2.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[1].amount").value(orders2.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAll() throws Exception {
        OrderClient orders = orderClientRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?amount=10&paymentCustomer=1&paymentClient=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[1].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[1].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[1].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[1].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[1].amount").value(orders.getAmount()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAmountWithPageable() throws Exception {
        OrderClient orders = orderClientRepository.findById(8L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/orders?sort=amount,DESC")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[1].id").value(orders.getId()))
                .andExpect(jsonPath("$.content[1].placeId").value(orders.getPlaceId()))
                .andExpect(jsonPath("$.content[1].paymentClient").value(orders.getPaymentClient().getId()))
                .andExpect(jsonPath("$.content[1].paymentCustomer").value(orders.getPaymentCustomer().getId()))
                .andExpect(jsonPath("$.content[1].amount").value(orders.getAmount()));
    }

    @Test
    @Sql("/scripts/order/create.sql")
    public void testBuy() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                2L,
                1L
        );
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(dto.getPaymentClient())).thenReturn(true);
        when(eventService.getAmount(dto.getPlaceId())).thenReturn(10D);
        doNothing().when(eventService).buyPlace(dto.getPlaceId());

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @Test
    @Sql("/scripts/order/create.sql")
    public void testBuyWithPlaceNotFoundException() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                2L,
                1L
        );
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(false);

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("/scripts/order/create.sql")
    public void testBuyWithCustomerNotFoundException() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                2L,
                1L
        );
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(false);

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Customer not found"));
    }

    @Test
    @Sql("/scripts/order/create.sql")
    public void testBuyWithClientNotFoundException() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                2L,
                1L
        );
        when(eventService.existsActivePlace(dto.getPlaceId())).thenReturn(true);
        when(userService.existCustomerByPaymentId(dto.getPaymentCustomer())).thenReturn(true);
        when(userService.existClientByPaymentId(dto.getPaymentClient())).thenReturn(false);

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Client not found"));
    }

    @Test
    public void testBuyWithPlaceIdNull() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                null,
                2L,
                1L
        );

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBuyWithPaymentClientNull() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                null,
                1L
        );

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBuyWithPaymentCustomerNull() throws Exception {
        OrderClientCreateDto dto = new OrderClientCreateDto(
                1L,
                null,
                1L
        );

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

}