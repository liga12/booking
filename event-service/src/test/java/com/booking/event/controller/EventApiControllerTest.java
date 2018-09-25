package com.booking.event.controller;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.service.event.EventService;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EventApiController.class)
public class EventApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @MockBean
    private EventService eventService;

    @MockBean
    private OrganizationService organizationService;

    @Test
    public void testExistsActivePlace() throws Exception {
        Long placeId = 1L;
        when(placeService.existActivePlace(placeId)).thenReturn(true);

        mockMvc.perform(get("/event-api/existActive/{placeId}", placeId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(placeService, times(1)).existActivePlace(placeId);
    }

    @Test
    public void testExistsBuyPlace() throws Exception {
        Long placeId = 1L;
        when(placeService.existBuyPlace(placeId)).thenReturn(true);

        mockMvc.perform(get("/event-api/existBuy/{placeId}", placeId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(placeService, times(1)).existBuyPlace(placeId);
    }

    @Test
    public void testGetAmount() throws Exception {
        PlaceOutcomeDto dto = new PlaceOutcomeDto();
        dto.setId(1L);
        dto.setPrice(10D);
        when(placeService.getById(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/event-api/getAmount/{placeId}", dto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getPrice()));

        verify(placeService, times(1)).getById(dto.getId());
    }

    @Test
    public void testBuyPlace() throws Exception {
        Long placeId = 1L;
        doNothing().when(placeService).buyPlace(placeId);

        mockMvc.perform(put("/event-api/{placeId}", placeId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(placeService, times(1)).buyPlace(placeId);
    }

    @Test
    public void testGetPlace() throws Exception {
        PlaceOutcomeDto dto = new PlaceOutcomeDto(
                1L,
                10D,
                1,
                1,
                PlaceStatusType.ACTIVE,
                1L,
                SectionType.FIRST_ROW
        );
        when(placeService.getById(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/event-api/getPlace/{placeId}", dto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.price").value(dto.getPrice()))
                .andExpect(jsonPath("$.number").value(dto.getNumber()))
                .andExpect(jsonPath("$.row").value(dto.getRow()))
                .andExpect(jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(jsonPath("$.event").value(dto.getEvent()))
                .andExpect(jsonPath("$.sectionType").value(dto.getSectionType().toString()));

        verify(placeService, times(1)).getById(dto.getId());
    }

    @Test
    public void testExistsEvent() throws Exception {
        Long eventId = 1L;
        when(eventService.existById(eventId)).thenReturn(true);

        mockMvc.perform(get("/event-api/existsEvent/{eventId}", eventId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(eventService, times(1)).existById(eventId);
    }

    @Test
    public void testGetEvent() throws Exception {
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

        mockMvc.perform(get("/event-api/getEvent/{eventId}", dto.getId())
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
    public void testExistsOrganization() throws Exception {
        Long organizationId = 1L;
        when(organizationService.exists(organizationId)).thenReturn(true);

        mockMvc.perform(get("/event-api/existsOrganization/{organizationId}", organizationId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(organizationService, times(1)).exists(organizationId);
    }

    @Test
    public void testGetOrganizationPhone() throws Exception {
        Long organizationId = 1L;
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto();
        organizationOutcomeDto.setPhone("1111");
        when(organizationService.getById(organizationId)).thenReturn(organizationOutcomeDto);

        mockMvc.perform(get("/event-api/getOrganizationPhone/{organizationId}", organizationId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(organizationOutcomeDto.getPhone()));

        verify(organizationService, times(1)).getById(organizationId);
    }
}