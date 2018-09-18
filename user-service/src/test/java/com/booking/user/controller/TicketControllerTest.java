package com.booking.user.controller;

import com.booking.user.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    public void testGetTicket() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 2L;
        Double cost = 10D;
        String url = "url";
        Mockito.when(ticketService.getTicketUrl(placeId, paymentClientId, cost)).thenReturn(url);

        mockMvc.perform(get("/tickets//{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(url));
    }

    @Test
    public void testGetPdf() throws Exception {
        String path = "path";
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream("/home/user/11111/ticket.pdf");
        inputStreamResource.getInputStream().read();
        Mockito.when(ticketService.getFile(path)).thenReturn(new InputStreamResource(new FileInputStream("/home/user/11111/ticket.pdf")));

        int i;
        while ((i = inputStreamResource.getInputStream().read()) != -1) {
            System.out.print(i);


            mockMvc.perform(get("/tickets/getPdf?path=path")
                    .accept(MediaType.APPLICATION_PDF))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                    .andExpect(content().bytes()).value(url));
        }

    }