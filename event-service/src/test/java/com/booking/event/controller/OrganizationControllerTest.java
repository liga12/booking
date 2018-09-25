package com.booking.event.controller;

import com.booking.event.service.organization.OrganizationService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import com.google.common.collect.Sets;
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

import java.util.Set;

import static com.booking.event.util.Converter.mapToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrganizationService organizationService;

    @InjectMocks
    private OrganizationController organizationController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(organizationController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        Set<Long> events = Sets.newHashSet(1L);
        OrganizationOutcomeDto dto = new OrganizationOutcomeDto(
                1L,
                "name",
                "loc",
                "111",
                "email",
                true,
                events,
                "1"
        );
        when(organizationService.getAll(
                any(OrganizationFindDto.class),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(Lists.newArrayList(dto)));

        mockMvc.perform(get("/organizations?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(dto.getName()))
                .andExpect(jsonPath("$.content[0].location").value(dto.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(dto.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(dto.getEvents().iterator().next()))
                .andExpect(jsonPath("$.content[0].customerId").value(dto.getCustomerId()));

        verify(organizationService, times(1)).getAll(any(OrganizationFindDto.class), any(Pageable.class));
    }

    @Test
    public void testGetById() throws Exception {
        Set<Long> events = Sets.newHashSet(1L);
        OrganizationOutcomeDto dto = new OrganizationOutcomeDto(
                1L,
                "name",
                "loc",
                "111",
                "email",
                true,
                events,
                "1"
        );
        when(organizationService.getById(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/organizations/{id}", dto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.location").value(dto.getLocation()))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.visible").value(dto.getVisible()))
                .andExpect(jsonPath("$.events[0]").value(dto.getEvents().iterator().next()))
                .andExpect(jsonPath("$.customerId").value(dto.getCustomerId()));

        verify(organizationService, times(1)).getById(dto.getId());
    }

    @Test
    public void testCreate() throws Exception {
        Long id = 1L;
        OrganizationCreateDto createDto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        when(organizationService.create(createDto)).thenReturn(id);

        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(organizationService, times(1)).create(createDto);
    }

    @Test
    public void testUpdate() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "1111111111",
                "email@gmail.com"
        );
        when(organizationService.update(any(OrganizationUpdateDto.class))).thenReturn(dto.getId());

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));

        verify(organizationService, times(1)).update(any(OrganizationUpdateDto.class));
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(organizationService).delete(id);

        mockMvc.perform(delete("/organizations/{id}", id))
                .andExpect(status().isOk());

        verify(organizationService).delete(id);
    }
}