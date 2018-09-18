package com.booking.event.transport.mapper;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.service.event.EventService;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganizationMapperTest {

    @InjectMocks
    private OrganizationMapperImpl organizationMapper;

    @Mock
    private EventService eventService;

    @Mock
    private OrganizationService organizationService;

    @Test
    public void testToDto() {
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        Set<AbstractEvent> events = Sets.newHashSet(event);
        Organization organization = new Organization(
                1L,
                "name",
                "loc",
                "1111",
                "email",
                true,
                events,
                "1");
        when(eventService.getIdFromEntity(organization.getEvents())).thenReturn(Sets.newHashSet(event.getId()));

        OrganizationOutcomeDto result = organizationMapper.toDto(organization);

        assertEquals(organization.getId(), result.getId());
        assertEquals(organization.getName(), result.getName());
        assertEquals(organization.getLocation(), result.getLocation());
        assertEquals(organization.getPhone(), result.getPhone());
        assertEquals(organization.getEmail(), result.getEmail());
        assertEquals(organization.getVisible(), result.getVisible());
        assertEquals(organization.getEvents().size(), result.getEvents().size());
        assertEquals(organization.getEvents()
                        .iterator()
                        .next()
                        .getId(),
                result.getEvents()
                        .iterator()
                        .next()
        );
        assertEquals(organization.getId(), result.getId());
    }


    @Test
    public void testOrganizationCreateDtoToEntity() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111",
                "email",
                "1"
        );

        Organization result = organizationMapper.toEntity(dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getCustomerId(), result.getCustomerId());
    }


    @Test
    public void testOrganizationUpdateDtoToEntity() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "111",
                "email"
        );
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        Set<Long> events = Sets.newHashSet(event.getId());
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(
                1L,
                "name",
                "loc",
                "111",
                "email",
                true,
                events,
                "1"
        );
        when(organizationService.getById(dto.getId())).thenReturn(organizationOutcomeDto);
        when(eventService.getById(organizationOutcomeDto.getEvents()))
                .thenReturn(Sets.newHashSet(event));

        Organization result = organizationMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    public void testOrganizationOutcomeDtoToEntity() {
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        Set<Long> events = Sets.newHashSet(event.getId());
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
        when(eventService.getById(dto.getEvents())).thenReturn(Sets.newHashSet(event));

        Organization result = organizationMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getVisible(), result.getVisible());
        assertEquals(dto.getEvents().size(), result.getEvents().size());
        assertEquals(dto.getEvents()
                        .iterator()
                        .next(),
                result.getEvents()
                        .iterator()
                        .next().getId()
        );
        assertEquals(dto.getId(), result.getId());
    }
}