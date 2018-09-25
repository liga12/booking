package com.booking.event.controller;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import org.assertj.core.util.Lists;
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
public class PlaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceController placeController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(placeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        PlaceOutcomeDto dto = new PlaceOutcomeDto(
                1L,
                10D,
                1,
                1,
                PlaceStatusType.ACTIVE,
                1L,
                SectionType.FIRST_ROW
        );
        when(placeService.getAll(
                any(PlaceFindDto.class),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(Lists.newArrayList(dto)));

        mockMvc.perform(get("/places?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].price").value(dto.getPrice()))
                .andExpect(jsonPath("$.content[0].number").value(dto.getNumber()))
                .andExpect(jsonPath("$.content[0].row").value(dto.getRow()))
                .andExpect(jsonPath("$.content[0].status").value(dto.getStatus().toString()))
                .andExpect(jsonPath("$.content[0].event").value(dto.getEvent()))
                .andExpect(jsonPath("$.content[0].sectionType").value(dto.getSectionType().toString()));

        verify(placeService, times(1)).getAll(any(PlaceFindDto.class), any(Pageable.class));
    }

    @Test
    public void testGetById() throws Exception {
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

        mockMvc.perform(get("/places/{id}", dto.getId())
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
    public void testCreate() throws Exception {
        Long id = 1L;
        PlaceCreateDto createDto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );
        when(placeService.create(createDto)).thenReturn(id);

        mockMvc.perform(put("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(placeService, times(1)).create(createDto);
    }

    @Test
    public void testUpdate() throws Exception {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );
        when(placeService.update(dto)).thenReturn(dto.getId());

        mockMvc.perform(post("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));

        verify(placeService, times(1)).update(dto);
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(placeService).delete(id);

        mockMvc.perform(delete("/places/{id}", id))
                .andExpect(status().isOk());

        verify(placeService).delete(id);
    }
}