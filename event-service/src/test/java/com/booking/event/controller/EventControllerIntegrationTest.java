package com.booking.event.controller;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
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

import static com.booking.event.util.Converter.mapToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L))
                .andExpect(jsonPath("$.content[1].id").value(event2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(event2.getName()))
                .andExpect(jsonPath("$.content[1].description").value(event2.getDescription()))
                .andExpect(jsonPath("$.content[1].date").value(event2.getDate()))
                .andExpect(jsonPath("$.content[1].location").value(event2.getLocation()))
                .andExpect(jsonPath("$.content[1].photoUrl").value(event2.getPhotoUrl()))
                .andExpect(jsonPath("$.content[1].artists").value(event2.getArtists()))
                .andExpect(jsonPath("$.content[1].organization").value(event2.getOrganization().getId()))
                .andExpect(jsonPath("$.content[1].places[0]").value(3L))
                .andExpect(jsonPath("$.content[1].places[1]").value(4L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=1&id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByName() throws Exception {
        AbstractEvent event = eventRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=1&name=nr6amee214")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByDescription() throws Exception {
        AbstractEvent event = eventRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=1&description=d2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByDate() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=2&startDate=1&endDate2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L))
                .andExpect(jsonPath("$.content[1].id").value(event2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(event2.getName()))
                .andExpect(jsonPath("$.content[1].description").value(event2.getDescription()))
                .andExpect(jsonPath("$.content[1].date").value(event2.getDate()))
                .andExpect(jsonPath("$.content[1].location").value(event2.getLocation()))
                .andExpect(jsonPath("$.content[1].photoUrl").value(event2.getPhotoUrl()))
                .andExpect(jsonPath("$.content[1].artists").value(event2.getArtists()))
                .andExpect(jsonPath("$.content[1].organization").value(event2.getOrganization().getId()))
                .andExpect(jsonPath("$.content[1].places[0]").value(3L))
                .andExpect(jsonPath("$.content[1].places[1]").value(4L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByLocation() throws Exception {
        AbstractEvent event = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=2&location=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(3L))
                .andExpect(jsonPath("$.content[0].places[1]").value(4L))
                .andExpect(jsonPath("$.content[1].id").value(event2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(event2.getName()))
                .andExpect(jsonPath("$.content[1].description").value(event2.getDescription()))
                .andExpect(jsonPath("$.content[1].date").value(event2.getDate()))
                .andExpect(jsonPath("$.content[1].location").value(event2.getLocation()))
                .andExpect(jsonPath("$.content[1].photoUrl").value(event2.getPhotoUrl()))
                .andExpect(jsonPath("$.content[1].artists").value(event2.getArtists()))
                .andExpect(jsonPath("$.content[1].organization").value(event2.getOrganization().getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPhotoUrl() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=2&photoUrl=url1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L))
                .andExpect(jsonPath("$.content[1].id").value(event2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(event2.getName()))
                .andExpect(jsonPath("$.content[1].description").value(event2.getDescription()))
                .andExpect(jsonPath("$.content[1].date").value(event2.getDate()))
                .andExpect(jsonPath("$.content[1].location").value(event2.getLocation()))
                .andExpect(jsonPath("$.content[1].photoUrl").value(event2.getPhotoUrl()))
                .andExpect(jsonPath("$.content[1].artists").value(event2.getArtists()))
                .andExpect(jsonPath("$.content[1].organization").value(event2.getOrganization().getId()))
                .andExpect(jsonPath("$.content[1].places[0]").value(3L))
                .andExpect(jsonPath("$.content[1].places[1]").value(4L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByArtists() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=2&artists=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L))
                .andExpect(jsonPath("$.content[1].id").value(event2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(event2.getName()))
                .andExpect(jsonPath("$.content[1].description").value(event2.getDescription()))
                .andExpect(jsonPath("$.content[1].date").value(event2.getDate()))
                .andExpect(jsonPath("$.content[1].location").value(event2.getLocation()))
                .andExpect(jsonPath("$.content[1].photoUrl").value(event2.getPhotoUrl()))
                .andExpect(jsonPath("$.content[1].artists").value(event2.getArtists()))
                .andExpect(jsonPath("$.content[1].organization").value(event2.getOrganization().getId()))
                .andExpect(jsonPath("$.content[1].places[0]").value(3L))
                .andExpect(jsonPath("$.content[1].places[1]").value(4L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByOrganization() throws Exception {
        AbstractEvent event = eventRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=1&organization=3")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPlace() throws Exception {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=3&places=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()))
                .andExpect(jsonPath("$.content[0].places[0]").value(1L))
                .andExpect(jsonPath("$.content[0].places[1]").value(2L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAllAndPageable() throws Exception {
        AbstractEvent event = eventRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events?page=0&size=3&name=name&sort=id,DESC")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(event.getId()))
                .andExpect(jsonPath("$.content[0].name").value(event.getName()))
                .andExpect(jsonPath("$.content[0].description").value(event.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(event.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(event.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(event.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(event.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(event.getOrganization().getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() throws Exception {
        AbstractEvent dto = eventRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/events/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()))
                .andExpect(jsonPath("$.date").value(dto.getDate()))
                .andExpect(jsonPath("$.location").value(dto.getLocation()))
                .andExpect(jsonPath("$.photoUrl").value(dto.getPhotoUrl()))
                .andExpect(jsonPath("$.artists").value(dto.getArtists()))
                .andExpect(jsonPath("$.places[0]").value(1L))
                .andExpect(jsonPath("$.places[1]").value(2L));
    }

    @Test
    public void testGetByIdNotExist() throws Exception {
        mockMvc.perform(get("/events/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not found"));
    }

    @Test
    @Sql("/scripts/createEvent.sql")
    public void testCreate() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(14444444444L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @Test
    @Sql("/scripts/createEvent.sql")
    public void testCreateWithNotCorrectDateException() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Not correct date"));
    }

    @Test
    @Sql("/scripts/createEvent.sql")
    public void testCreateWithNotActiveOrganization() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not active"));
    }

    @Test
    public void testCreateWithNameNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName(null);
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithNameEmpty() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithTypeNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);
        dto.setType(null);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithNotCorrectType() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto).replace("CINEMA", "C")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithDateNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(null);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithLocationNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(16666666666666666L);
        dto.setLocation(null);
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithLocationEmpty() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithPhotoUrlNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(16666666666666666L);
        dto.setLocation("l");
        dto.setPhotoUrl(null);
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithPhotoUrlEmpty() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("l");
        dto.setPhotoUrl("");
        dto.setArtists("artist");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithArtistlNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(16666666666666666L);
        dto.setLocation("l");
        dto.setPhotoUrl("url");
        dto.setArtists(null);
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithArtistEmpty() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(166666666666L);
        dto.setLocation("l");
        dto.setPhotoUrl("l");
        dto.setArtists("");
        dto.setOrganization(2L);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithOrganizationNull() throws Exception {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(16666666666666666L);
        dto.setLocation("l");
        dto.setPhotoUrl("url");
        dto.setArtists("art");
        dto.setOrganization(null);
        dto.setMinAge(12);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdate() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(15555555555555L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdateNotCorrectDateException() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Not correct date"));
    }

    @Test
    public void testUpdateEventNotFoundException() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name21");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not found"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdateEventOrganizationNotActiveException() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name21");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not active"));
    }

    @Test
    public void testUpdateEventIdNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(null);
        dto.setName("name21");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventNameNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName(null);
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventNameEmpty() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventTypeNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);
        dto.setType(null);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventNotCorrectType() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto).replace("CINEMA", "Ty")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventDateNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(null);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventLocationNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation(null);
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventLocationEmpty() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventPhotoUrlNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl(null);
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventPhotoUrlEmpty() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventArtistNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("url");
        dto.setArtists(null);
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventArtistEmpty() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("url");
        dto.setArtists("");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEventOrganizationNull() throws Exception {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999996L);
        dto.setLocation("loc");
        dto.setPhotoUrl("url");
        dto.setArtists("art");
        dto.setOrganization(null);
        dto.setMinAge(12);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDelete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visible").value(false));
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not found"));
    }

    @Test
    public void testDeleteIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().isMethodNotAllowed());
    }
}