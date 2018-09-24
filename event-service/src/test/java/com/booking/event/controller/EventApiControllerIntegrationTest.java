package com.booking.event.controller;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.persistence.repository.PlaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsActivePlace() throws Exception {
        mockMvc.perform(get("/event-api/existActive/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsActivePlaceIdNull() throws Exception {
        Long id = null;
        mockMvc.perform(get("/event-api/existActive{placeId}/", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testExistsActivePlaceIdNotExist() throws Exception {
        mockMvc.perform(get("/event-api/existActive/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsBuyPlace() throws Exception {
        mockMvc.perform(get("/event-api/existBuy/{placeId}", 3L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsBuyPlaceNull() throws Exception {
        Long id = null;
        mockMvc.perform(get("/event-api/existBuy/{placeId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testExistsBuyPlaceNotExist() throws Exception {
        mockMvc.perform(get("/event-api/existBuy/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAmount() throws Exception {
        mockMvc.perform(get("/event-api/getAmount/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(105L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAmountIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(get("/event-api/getAmount/{placeId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testGetAmountNotExist() throws Exception {
        mockMvc.perform(get("/event-api/getAmount/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testBuyPlace() throws Exception {
        mockMvc.perform(put("/event-api/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuyPlaceIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(put("/event-api/{placeId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBuyPlaceNotExistPlace() throws Exception {
        mockMvc.perform(put("/event-api/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testBuyPlaceNotExistActivePlace() throws Exception {
        mockMvc.perform(put("/event-api/{placeId}", 3L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetPlace() throws Exception {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/event-api/getPlace/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(place.getId()))
                .andExpect(jsonPath("$.price").value(place.getPrice()))
                .andExpect(jsonPath("$.number").value(place.getNumber()))
                .andExpect(jsonPath("$.row").value(place.getRow()))
                .andExpect(jsonPath("$.status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.sectionType").value(place.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetPlaceIdNull() throws Exception {
        Long id = null;
        mockMvc.perform(get("/event-api/getPlace/{placeId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testGetPlaceNotExist() throws Exception {
        mockMvc.perform(get("/event-api/getPlace/{placeId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsEvent() throws Exception {
        mockMvc.perform(get("/event-api/existsEvent/{eventId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testExistsEventIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(get("/event-api/existsEvent/{eventId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testExistsEventIdNotExist() throws Exception {
        mockMvc.perform(get("/event-api/existsEvent/{eventId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("false"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetEvent() throws Exception {
        Long id = 1L;
        AbstractEvent event = eventRepository.findById(id).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/event-api/getEvent/{eventId}", event.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.name").value(event.getName()))
                .andExpect(jsonPath("$.description").value(event.getDescription()))
                .andExpect(jsonPath("$.date").value(event.getDate()))
                .andExpect(jsonPath("$.location").value(event.getLocation()))
                .andExpect(jsonPath("$.photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.artists").value(event.getArtists()))
                .andExpect(jsonPath("$.organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.places[0]").value(1L))
                .andExpect(jsonPath("$.places[1]").value(2L));
    }

    @Test
    public void testGetEventIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(get("/event-api/getEvent/{eventId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testGetEventNotExist() throws Exception {
        mockMvc.perform(get("/event-api/getEvent/{eventId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsOrganization() throws Exception {
        mockMvc.perform(get("/event-api/existsOrganization/{organizationId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistsOrganizationIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(get("/event-api/existsOrganization/{organizationId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testExistsOrganizationNotExist() throws Exception {
        mockMvc.perform(get("/event-api/existsOrganization/{organizationId}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetOrganizationPhone() throws Exception {
        Long organizationId = 1L;
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/event-api/getOrganizationPhone/{organizationId}", organizationId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(organization.getPhone()));
    }

    @Test
    public void testGetOrganizationPhoneIdNull() throws Exception {
        Long organizationId = null;

        mockMvc.perform(get("/event-api/getOrganizationPhone/{organizationId}", organizationId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testGetOrganizationPhoneNotExist() throws Exception {
        Long organizationId = 1L;

        mockMvc.perform(get("/event-api/getOrganizationPhone/{organizationId}", organizationId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not found"));
    }


}