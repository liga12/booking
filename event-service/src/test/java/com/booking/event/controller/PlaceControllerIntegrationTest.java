package com.booking.event.controller;

import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
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
public class PlaceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() throws Exception {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()))
                .andExpect(jsonPath("$.content[1].id").value(place2.getId()))
                .andExpect(jsonPath("$.content[1].price").value(place2.getPrice()))
                .andExpect(jsonPath("$.content[1].number").value(place2.getNumber()))
                .andExpect(jsonPath("$.content[1].row").value(place2.getRow()))
                .andExpect(jsonPath("$.content[1].status").value(place2.getStatus().toString()))
                .andExpect(jsonPath("$.content[1].event").value(place2.getEvent().getId()))
                .andExpect(jsonPath("$.content[1].sectionType").value(place2.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() throws Exception {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPrice() throws Exception {
        Place place = placeRepository.findById(3L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&price=150")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()))
                .andExpect(jsonPath("$.content[1].id").value(place2.getId()))
                .andExpect(jsonPath("$.content[1].price").value(place2.getPrice()))
                .andExpect(jsonPath("$.content[1].number").value(place2.getNumber()))
                .andExpect(jsonPath("$.content[1].row").value(place2.getRow()))
                .andExpect(jsonPath("$.content[1].status").value(place2.getStatus().toString()))
                .andExpect(jsonPath("$.content[1].event").value(place2.getEvent().getId()))
                .andExpect(jsonPath("$.content[1].sectionType").value(place2.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByNumber() throws Exception {
        Place place = placeRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&number=12")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByRow() throws Exception {
        Place place = placeRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&row=41")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByStatus() throws Exception {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&status=ACTIVE")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()))
                .andExpect(jsonPath("$.content[1].id").value(place2.getId()))
                .andExpect(jsonPath("$.content[1].price").value(place2.getPrice()))
                .andExpect(jsonPath("$.content[1].number").value(place2.getNumber()))
                .andExpect(jsonPath("$.content[1].row").value(place2.getRow()))
                .andExpect(jsonPath("$.content[1].status").value(place2.getStatus().toString()))
                .andExpect(jsonPath("$.content[1].event").value(place2.getEvent().getId()))
                .andExpect(jsonPath("$.content[1].sectionType").value(place2.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEvent() throws Exception {
        Place place = placeRepository.findById(3L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&event=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()))
                .andExpect(jsonPath("$.content[1].id").value(place2.getId()))
                .andExpect(jsonPath("$.content[1].price").value(place2.getPrice()))
                .andExpect(jsonPath("$.content[1].number").value(place2.getNumber()))
                .andExpect(jsonPath("$.content[1].row").value(place2.getRow()))
                .andExpect(jsonPath("$.content[1].status").value(place2.getStatus().toString()))
                .andExpect(jsonPath("$.content[1].event").value(place2.getEvent().getId()))
                .andExpect(jsonPath("$.content[1].sectionType").value(place2.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllBySectionType() throws Exception {
        Place place = placeRepository.findById(3L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&sectionType=MIDDLE_ROW")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()))
                .andExpect(jsonPath("$.content[1].id").value(place2.getId()))
                .andExpect(jsonPath("$.content[1].price").value(place2.getPrice()))
                .andExpect(jsonPath("$.content[1].number").value(place2.getNumber()))
                .andExpect(jsonPath("$.content[1].row").value(place2.getRow()))
                .andExpect(jsonPath("$.content[1].status").value(place2.getStatus().toString()))
                .andExpect(jsonPath("$.content[1].event").value(place2.getEvent().getId()))
                .andExpect(jsonPath("$.content[1].sectionType").value(place2.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAllAndPageable() throws Exception {
        Place place = placeRepository.findById(4L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places?page=0&size=2&sectionType=MIDDLE_ROW&status=ACTIVE&row=41&sort=id,DESC")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(place.getId()))
                .andExpect(jsonPath("$.content[0].price").value(place.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(place.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(place.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(place.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(place.getEvent().getId()))
                .andExpect(jsonPath("$.content[0].sectionType").value(place.getSectionType().toString()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() throws Exception {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/places/{id}", place.getId())
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
    public void testGetByIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(get("/places/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByNotExist() throws Exception {
        mockMvc.perform(get("/places/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    @Sql("classpath:scripts/create.sql")
    public void testCreate() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2L));
    }

    @Test
    @Sql("classpath:scripts/create.sql")
    public void testCreateExist() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place exist"));
    }

    @Test
    @Sql("classpath:scripts/create.sql")
    public void testCreatePlaceNotActive() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                2L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Event not active"));
    }

    @Test
    public void testCreatePriceNull() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                null,
                1,
                1,
                2L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateNumberNull() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                null,
                1,
                2L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateRowNull() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                null,
                2L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateEventNull() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                null,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateSectionTypeNull() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                null
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateNotCorrectSectionType() throws Exception {
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto).replace("FIRST_ROW", "G")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdate() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));
    }

    @Test
    public void testUpdateNotExistId() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    public void testUpdateIdNull() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                null,
                10D,
                PlaceStatusType.ACTIVE
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePriceNull() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                null,
                PlaceStatusType.ACTIVE
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStatusNull() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                null
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNotCorrectStatus() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto).replace("ACTIVE", "D")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/places/{id}", 1L))
                .andExpect(status().isOk());

        mockMvc.perform(get("/places/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(PlaceStatusType.NOT_ACTIVE.toString()));
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(get("/places/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Place not found"));
    }

    @Test
    public void testDeleteIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(delete("/places/{id}", id))
                .andExpect(status().isMethodNotAllowed());
    }
}