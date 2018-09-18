package com.booking.user.controller;

import com.booking.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserApiController.class)
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testExistsCustomer() throws Exception {
        String customerId = "1";
        when(userService.existsCustomer(customerId)).thenReturn(true);

        mockMvc.perform(get("/user-api/{customerId}",customerId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(userService, times(1)).existsCustomer(customerId);
    }

    @Test
    public void testExistCustomerByPaymentId() throws Exception {
        Long paymentId = 1L;
        when(userService.existsCustomerByPaymentId(paymentId)).thenReturn(true);

        mockMvc.perform(get("/user-api/customer/{paymentId}",paymentId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(userService, times(1)).existsCustomerByPaymentId(paymentId);
    }

    @Test
    public void testExistClientByPaymentId() throws Exception {
        Long paymentId = 1L;
        when(userService.existsClientByPaymentId(paymentId)).thenReturn(true);

        mockMvc.perform(get("/user-api/client/{paymentId}",paymentId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(userService, times(1)).existsClientByPaymentId(paymentId);
    }
}