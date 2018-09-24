package com.booking.payment.controller;

import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        PaymentOutcomeDto dto = new PaymentOutcomeDto(
                1L,
                "token",
                Sets.newLinkedHashSet(1L),
                Sets.newLinkedHashSet(2L)
        );
        when(paymentService.getAll(
                any(PaymentFindDto.class),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(Lists.newArrayList(dto)));

        mockMvc.perform(get("/payments?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].token").value(dto.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(dto.getClient().iterator().next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(dto.getCustomer().iterator().next()));

        verify(paymentService, times(1)).getAll(any(PaymentFindDto.class), any(Pageable.class));
    }

    @Test
    public void testCreate() throws Exception {
        Long id = 1L;
        PaymentCreateDto createDto = new PaymentCreateDto("111111");
        when(paymentService.create(createDto)).thenReturn(id);

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(paymentService, times(1)).create(createDto);
    }

    @Test
    public void testCreateMore6() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto("1111111");

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateLess6() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto("11111");

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }
}