package com.booking.user.controller;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.TicketService;
import com.booking.user.service.feign.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;

    @MockBean
    private EventService eventService;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetPdf() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        String organizationPhone = "111";
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(true);
        when(eventService.getOrganizationPhone(event.getOrganization())).thenReturn(organizationPhone);
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);
        String ticketUrl = ticketService.getTicketUrl(placeId, paymentClientId, cost);

        mockMvc.perform(get("/tickets/getPdf")
                .accept(MediaType.APPLICATION_PDF)
                .param("path", ticketUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    public void testGetPdfBadPath() throws Exception {
        String path = "12";

        mockMvc.perform(get("/tickets/getPdf")
                .accept(MediaType.APPLICATION_PDF)
                .param("path", "12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(jsonPath("$.error").value(path + " (No such file or directory)"));
    }

    @Test
    public void testGetTicket() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        String organizationPhone = "111";
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(true);
        when(eventService.getOrganizationPhone(event.getOrganization())).thenReturn(organizationPhone);
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("path", "12"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTicketWithPlaceIdNull() throws Exception {

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", null, 1L, 10d)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTicketWithPaymentClientIdNull() throws Exception {

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", 1L, null, 10d)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTicketWithCostNull() throws Exception {

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", 1L, 1L, null)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTicketWithPlaceNotFoundException() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        when(eventService.existsBuyPlace(placeId)).thenReturn(false);

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    public void testGetTicketWithEventNotFoundException() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(false);

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not found"));
    }

    @Test
    public void testGetTicketWithOrganizationNotFoundException() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(false);

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not found"));
    }

    @Test
    public void testGetTicketWithUserNotFoundException() throws Exception {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        String organizationPhone = "111";
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(true);
        when(eventService.getOrganizationPhone(event.getOrganization())).thenReturn(organizationPhone);

        mockMvc.perform(get("/tickets/{placeId}/{paymentClientId}/{cost}", placeId, paymentClientId, cost)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("User not found"));
    }
}
