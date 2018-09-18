package com.booking.event.controller;

import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.service.event.EventService;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.booking.event.util.Converter.mapToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);
        dto.setPlaces(Sets.newLinkedHashSet(1L));
        when(eventService.getAll(
                any(EventFindDto.class),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(Lists.newArrayList(dto)));

        mockMvc.perform(get("/events?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(dto.getName()))
                .andExpect(jsonPath("$.content[0].description").value(dto.getDescription()))
                .andExpect(jsonPath("$.content[0].date").value(dto.getDate()))
                .andExpect(jsonPath("$.content[0].location").value(dto.getLocation()))
                .andExpect(jsonPath("$.content[0].photoUrl").value(dto.getPhotoUrl()))
                .andExpect(jsonPath("$.content[0].artists").value(dto.getArtists()))
                .andExpect(jsonPath("$.content[0].organization").value(dto.getOrganization()))
                .andExpect(jsonPath("$.content[0].minAge").value(dto.getMinAge()))
                .andExpect(jsonPath("$.content[0].places[0]").value(dto.getPlaces().iterator().next()));

        verify(eventService, times(1)).getAll(any(EventFindDto.class), any(Pageable.class));
    }

    @Test
    public void testGetById() throws Exception {
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);
        dto.setPlaces(Sets.newLinkedHashSet(1L));
        when(eventService.getById(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/events/{id}", dto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()))
                .andExpect(jsonPath("$.date").value(dto.getDate()))
                .andExpect(jsonPath("$.location").value(dto.getLocation()))
                .andExpect(jsonPath("$.photoUrl").value(dto.getPhotoUrl()))
                .andExpect(jsonPath("$.artists").value(dto.getArtists()))
                .andExpect(jsonPath("$.organization").value(dto.getOrganization()))
                .andExpect(jsonPath("$.minAge").value(dto.getMinAge()))
                .andExpect(jsonPath("$.places[0]").value(dto.getPlaces().iterator().next()));

        verify(eventService, times(1)).getById(dto.getId());
    }

    @Test
    public void testCreate() throws Exception {
        Long id = 1L;
        CinemaEventCreateDto createDto = new CinemaEventCreateDto();
        createDto.setName("name");
        createDto.setDescription("desc");
        createDto.setDate(1L);
        createDto.setLocation("loc");
        createDto.setPhotoUrl("photo");
        createDto.setArtists("artist");
        createDto.setOrganization(1L);
        createDto.setMinAge(12);
        when(eventService.create(createDto)).thenReturn(id);

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(eventService, times(1)).create(createDto);
    }

    @Test
    public void testUpdate() throws Exception {
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
        when(eventService.update(dto)).thenReturn(dto.getId());

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));

        verify(eventService, times(1)).update(dto);
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(eventService).delete(id);

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().isOk());

        verify(eventService).delete(id);
    }
}