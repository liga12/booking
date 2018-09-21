package com.booking.user.service;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.user.exception.EventNotFoundException;
import com.booking.user.exception.NotCorrectTicketDateException;
import com.booking.user.exception.OrganizationNotFoundException;
import com.booking.user.exception.PlaceNotFoundException;
import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.feign.EventService;
import com.booking.user.ticket.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.booking.user.asserts.Asserts.assertEqualArrays;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:pdf.properties")
public class TicketServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Environment environment;

    @Value("${pdf.folder}")
    private String pdfFolder;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetFile() throws IOException {
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
        String path = ticketUrl.substring(40);

        InputStreamResource result = ticketService.getFile(path);

        InputStream stream = new FileInputStream(path);
        assertEqualArrays(stream, result.getInputStream());
    }


    @Test(expected = FileNotFoundException.class)
    public void testGetFileWithFileNotFoundException() throws IOException {
        ticketService.getFile("/23/232/2323/23/232/323/345345.pdf");
    }

    @Test
    public void testGetTicketUrl() {
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
        String hostAndPort = "http://localhost:" + environment.getProperty("server.port") + "/";
        String controllerMethod = "tickets/getPdf?path=";
        String path = hostAndPort
                + controllerMethod
                + pdfFolder
                + System.currentTimeMillis() / 10000
                + user.getName()
                + user.getSurname()
                + ".pdf";

        String result = ticketService.getTicketUrl(placeId, paymentClientId, cost);


        assertEquals(path, result);
    }

    @Test(expected = PlaceNotFoundException.class)
    public void testGetTicketUrlPlaceIdNull() {
        Long placeId = null;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test(expected = PlaceNotFoundException.class)
    public void testGetTicketUrlWithPlaceNotFoundException() {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        when(eventService.existsBuyPlace(placeId)).thenReturn(false);
        when(eventService.getPlace(placeId)).thenReturn(place);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test(expected = EventNotFoundException.class)
    public void testGetTicketUrlEventIdNull() {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, null);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(1L);
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test(expected = EventNotFoundException.class)
    public void testGetTicketUrlWithEventNotFoundException() {
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
        when(eventService.existsEvent(place.getEvent())).thenReturn(false);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testGetTicketUrlOrganizationIdNull() {
        Long placeId = 1L;
        Long paymentClientId = 1L;
        Double cost = 10d;
        PlaceOutcomeDto place = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEventOutcomeDto event = new CinemaEventOutcomeDto();
        event.setName("name");
        event.setDate(1L);
        event.setOrganization(null);
        String organizationPhone = "111";
        when(eventService.existsBuyPlace(placeId)).thenReturn(true);
        when(eventService.getPlace(placeId)).thenReturn(place);
        when(eventService.existsEvent(place.getEvent())).thenReturn(true);
        when(eventService.getEvent(place.getEvent())).thenReturn(event);
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(true);
        when(eventService.getOrganizationPhone(event.getOrganization())).thenReturn(organizationPhone);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testGetTicketUrlWithOrganizationNotFoundException() {
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
        when(eventService.existsOrganization(event.getOrganization())).thenReturn(false);
        when(eventService.getOrganizationPhone(event.getOrganization())).thenReturn(organizationPhone);

        ticketService.getTicketUrl(placeId, paymentClientId, cost);
    }

    @Test
    public void testCreatePdf() {
        Ticket ticket = new Ticket(
                "name",
                "surname",
                "event",
                1,
                1,
                "date",
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test
    public void testCreatePdfNameNull() {
        Ticket ticket = new Ticket(
                null,
                "surname",
                "event",
                1,
                1,
                "date",
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test
    public void testCreatePdfSurnameNull() {
        Ticket ticket = new Ticket(
                "name",
                null,
                "event",
                1,
                1,
                "date",
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test
    public void testCreatePdfEventNull() {
        Ticket ticket = new Ticket(
                "name",
                "surname",
                null,
                1,
                1,
                "date",
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test
    public void testCreatePdfDateNull() {
        Ticket ticket = new Ticket(
                "name",
                "surname",
                "event",
                1,
                1,
                null,
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test
    public void testCreatePdfCostNull() {
        Ticket ticket = new Ticket(
                "name",
                "surname",
                "event",
                1,
                1,
                "date",
                10,
                "11"
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test(expected = NotCorrectTicketDateException.class)
    public void testCreatePdfWithNotCorrectTicketDateException() {
        ticketService.createPdf(null);
    }

    @Test
    public void testCreatePdfOrganizationPhoneNull() {
        Ticket ticket = new Ticket(
                "name",
                "surname",
                "event",
                1,
                1,
                "date",
                10,
                null
        );
        String path = pdfFolder + System.currentTimeMillis() / 10000 + ticket.getName() + ticket.getSurname() + ".pdf";

        String result = ticketService.createPdf(ticket);

        assertEquals(path, result);
    }

    @Test(expected = NotCorrectTicketDateException.class)
    public void testCreatePdfTicketNull() {
        Ticket ticket = null;

        ticketService.createPdf(ticket);
    }
}