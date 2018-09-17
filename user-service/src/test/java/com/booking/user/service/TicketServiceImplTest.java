package com.booking.user.service;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.user.service.feign.EventService;
import com.booking.user.ticket.Ticket;
import com.booking.user.transpor.dto.UserOutcomeDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TicketServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private Environment environment;

    @Mock
    private EventService eventService;

    @InjectMocks
    @Spy
    private TicketServiceImpl ticketService;

    @Test
    public void testGetTicketUrl() {
        String hostAndPort = "http://localhost:";
        String controllerMethod = "tickets/getPdf?path=";
        Long placeId = 1L;
        Long paymentClientId = 2L;
        Double cost = 10d;
        PlaceOutcomeDto placeOutcomeDto = new PlaceOutcomeDto();
        placeOutcomeDto.setNumber(1);
        placeOutcomeDto.setRow(1);
        placeOutcomeDto.setEvent(1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        UserOutcomeDto userOutcomeDto = new UserOutcomeDto();
        userOutcomeDto.setName("name");
        userOutcomeDto.setSurname("surname");
        String fileName = "12";
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(placeOutcomeDto);
        when(eventService.existsEvent(placeOutcomeDto.getEvent())).thenReturn(true);
        when(eventService.getEvent(placeOutcomeDto.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(true);
        when(eventService.getOrganizationPhone(placeOutcomeDto.getEvent())).thenReturn("11111");
        when(userService.getUserByPaymentId(paymentClientId)).thenReturn(userOutcomeDto);
        doReturn(fileName).when(ticketService).createPdf( any(Ticket.class));
        when(environment.getProperty("server.port")).thenReturn("8080");

        String result = ticketService.getTicketUrl(placeId, paymentClientId, cost);

        verify(eventService, times(1)).existsBuyPlace(placeId);
        verify(eventService, times(1)).getPlace(placeId);
        verify(eventService, times(1)).existsEvent(placeOutcomeDto.getEvent());
        verify(eventService, times(1)).getEvent(placeOutcomeDto.getEvent());
        verify(eventService, times(1)).existsOrganization(event.getOrganization());
        verify(eventService, times(1)).getOrganizationPhone(placeOutcomeDto.getEvent());
        verify(userService, times(1)).getUserByPaymentId(paymentClientId);
        verify(ticketService, times(1)).createPdf( any(Ticket.class));
        assertEquals(hostAndPort+ environment.getProperty("server.port")+"/"+controllerMethod+fileName, result);
    }

    @Test
    public void test(){

    }

}