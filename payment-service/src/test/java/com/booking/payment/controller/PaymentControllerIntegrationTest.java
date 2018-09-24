package com.booking.payment.controller;

import com.booking.payment.persistence.repository.PaymentRepository;
import com.booking.payment.service.payment.PaymentService;
import com.booking.payment.transpor.dto.PaymentCreateDto;
import com.booking.payment.transpor.dto.PaymentFindDto;
import com.booking.payment.transpor.dto.PaymentOutcomeDto;
import com.booking.payment.transpor.mapper.PaymentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static com.booking.payment.util.Converter.mapToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAll() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(2L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();


        mockMvc.perform(get("/payments?page=1&size=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllById() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(1L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();

        mockMvc.perform(get("/payments?page=0&size=1&id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].client[1]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].client[2]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[1]").value(customerIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[2]").value(customerIterator.next()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByToken() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(2L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();

        mockMvc.perform(get("/payments?page=0&size=1&token=11tr11")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllClient() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(1L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();

        mockMvc.perform(get("/payments?page=0&size=5&client=1&client=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllCustomer() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(1L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();

        mockMvc.perform(get("/payments?page=0&size=5&customer=1&customer=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()));
    }

    @Test
    @Sql("/scripts/order/init.sql")
    public void testGetAllByAll() throws Exception {
        PaymentOutcomeDto payments = paymentMapper.toDto(paymentRepository.findById(2L).orElseThrow(AssertionError::new));
        Iterator<Long> clientIterator = payments.getClient().iterator();
        Iterator<Long> customerIterator = payments.getCustomer().iterator();

        mockMvc.perform(get("/payments?page=0&size=5&id=2&token=11tr11")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(payments.getId()))
                .andExpect(jsonPath("$.content[0].token").value(payments.getToken()))
                .andExpect(jsonPath("$.content[0].client[0]").value(clientIterator.next()))
                .andExpect(jsonPath("$.content[0].customer[0]").value(customerIterator.next()));
    }

    @Test
    public void testCreate() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto("1ggggg");
        PaymentFindDto paymentFindDto = new PaymentFindDto();
        paymentFindDto.setToken("1ggggg");


        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(paymentService.getAll(
                        paymentFindDto,
                        PageRequest.of(0, 10)
                ).getContent()
                        .get(0)
                        .getId()));
    }

    @Test
    public void testCreateWithTokenNull() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto();
        createDto.setToken(null);

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithTokenSizeMore6() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto();
        createDto.setToken("1111111");

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithTokenSizLess6() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto();
        createDto.setToken("11111");

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithExistTokenException() throws Exception {
        PaymentCreateDto createDto = new PaymentCreateDto();
        createDto.setToken("111111");
        paymentService.create(createDto);

        mockMvc.perform(put("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Token exist"));
    }

}