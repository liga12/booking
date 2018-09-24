package com.booking.user.controller;

import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testExistsCustomer() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testExistsCustomerFalse() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testExistCustomerByPaymentId() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/customer/{id}", user.getPaymentId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testExistCustomerByPaymentIdFalse() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/customer/{id}", user.getPaymentId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testExistCustomerByPaymentIdNotExistId() throws Exception {

        mockMvc.perform(get("/user-api/customer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testExistClientByPaymentId() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/client/{id}", user.getPaymentId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testExistClientByPaymentIdFalse() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/user-api/client/{id}", user.getPaymentId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testExistClientByPaymentIdNotExistId() throws Exception {

        mockMvc.perform(get("/user-api/client/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}