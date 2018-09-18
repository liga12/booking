package com.booking.payment.controller;

import com.booking.payment.service.order.OrderClientService;
import com.booking.payment.transpor.dto.OrderClientCreateDto;
import com.booking.payment.transpor.dto.OrderClientFindDto;
import com.booking.payment.transpor.dto.OrderClientOutcomeDto;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.booking.payment.util.Converter.mapToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderClientService orderClientService;

    @InjectMocks
    private OrderClientController orderClientController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderClientController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        OrderClientOutcomeDto dto = new OrderClientOutcomeDto
                (1L, 1L, 2L, 1L, 10D);
        when(orderClientService.getAll(
                any(OrderClientFindDto.class),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(Lists.newArrayList(dto)));

        mockMvc.perform(get("/orders?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].placeId").value(dto.getPlaceId()))
                .andExpect(jsonPath("$.content[0].paymentClient").value(dto.getPaymentClient()))
                .andExpect(jsonPath("$.content[0].paymentCustomer").value(dto.getPaymentCustomer()))
                .andExpect(jsonPath("$.content[0].amount").value(dto.getAmount()));

        verify(orderClientService, times(1)).getAll(any(OrderClientFindDto.class), any(Pageable.class));
    }

    @Test
    public void testBuy() throws Exception {
        Long id = 1L;
        OrderClientCreateDto createDto = new OrderClientCreateDto(
                1L,
                2L,
                1L
        );
        when(orderClientService.create(any(OrderClientCreateDto.class))).thenReturn(id);

        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(orderClientService, times(1)).create(any(OrderClientCreateDto.class));
    }
}