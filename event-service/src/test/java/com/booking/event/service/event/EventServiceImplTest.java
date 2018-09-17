package com.booking.event.service.event;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import com.booking.event.transport.mapper.EventMapper;
import com.google.common.collect.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EventServiceImplTest {

    @InjectMocks
    @Spy
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private PlaceService placeService;

    @Test
    public void testGetAll() {
        Organization organization = new Organization();
        organization.setId(1L);
        Place place = new Place();
        place.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        event.setName("name");
        event.setDate(1000000L);
        event.setLocation("Kharkov");
        event.setPhotoUrl("photoUrl");
        event.setOrganization(organization);
        event.setPlaces(Sets.newHashSet(
                Lists.newArrayList(place)
                )
        );
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDate(1000000L);
        dto.setLocation("Kharkov");
        dto.setPhotoUrl("photoUrl");
        dto.setOrganization(organization.getId());
        dto.setPlaces(Sets.newHashSet(
                Lists.newArrayList(place.getId())
                )
        );
        PageImpl<AbstractEvent> page = new PageImpl(Lists.newArrayList(event));
        when(eventRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
                )
        ).thenReturn(page);
        when(eventMapper.toDto(event)).thenReturn(dto);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(
                new EventFindDto(),
                PageRequest.of(0, 100)
        );

        verify(eventRepository, times(1)).findAll(
                any(Specification.class),
                any(Pageable.class)
        );

        verify(eventMapper, times(1)).toDto(event);
        assertEquals(page.map(eventMapper::toDto), result);
    }

    @Test
    public void testGetById() {
        Organization organization = new Organization();
        organization.setId(1L);
        Place place = new Place();
        place.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        event.setName("name");
        event.setDate(1000000L);
        event.setLocation("Kharkov");
        event.setPhotoUrl("photoUrl");
        event.setOrganization(organization);
        event.setPlaces(Sets.newHashSet(
                Lists.newArrayList(place)
                )
        );
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDate(1000000L);
        dto.setLocation("Kharkov");
        dto.setPhotoUrl("photoUrl");
        dto.setOrganization(organization.getId());
        dto.setPlaces(Sets.newHashSet(
                Lists.newArrayList(place.getId())
                )
        );
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventMapper.toDto(event)).thenReturn(dto);

        AbstractEventOutcomeDto result = eventService.getById(id);

        verify(eventRepository, times(1)).findById(id);
        verify(eventMapper, times(1)).toDto(event);
        assertEquals(dto, result);
    }

    @Test
    public void testGetByIds() {
        Organization organization = new Organization();
        organization.setId(1L);
        Place place = new Place();
        place.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        event.setName("name");
        event.setDate(1000000L);
        event.setLocation("Kharkov");
        event.setPhotoUrl("photoUrl");
        event.setOrganization(organization);
        event.setPlaces(Sets.newHashSet(
                Lists.newArrayList(place)
                )
        );
        Set<Long> ids = Sets.newLinkedHashSet(1L);
        ArrayList<AbstractEvent> events = Lists.newArrayList(event);
        when(eventRepository.findAllById(ids)).thenReturn(events);

        Set<AbstractEvent> result = eventService.getById(ids);

        verify(eventRepository, times(1)).findAllById(ids);
        assertEquals(Sets.newLinkedHashSet(event), result);
    }

    @Test
    public void testCreate() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setDate(11111111111L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        doNothing().when(organizationService).validateOrganizationByActive(anyLong());
        when(eventMapper.toEntity(dto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);

        Long result = eventService.create(dto);

        verify(eventMapper, times(1)).toEntity(dto);
        verify(eventRepository, times(1)).save(event);
        assertEquals(event.getId(), result);
    }

    @Test
    public void testUpdate() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setDate(11111111111L);
        dto.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        when(eventRepository.existsById(dto.getId())).thenReturn(true);
        doNothing().when(organizationService).validateOrganizationByActive(anyLong());
        when(eventMapper.toEntity(dto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);

        Long result = eventService.update(dto);

        verify(eventRepository, times(1)).existsById(dto.getId());
        verify(eventMapper, times(1)).toEntity(dto);
        verify(eventRepository, times(1)).save(event);
        assertEquals(event.getId(), result);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        CinemaEvent event = new CinemaEvent();
        event.setPlaces(Sets.newLinkedHashSet(new Place()));
        doReturn(dto).when(eventService).getById(id);
        when(eventMapper.toEntity(dto)).thenReturn(event);
        doNothing().when(placeService).delete(anyLong());
        when(eventRepository.save(event)).thenReturn(event);

        eventService.delete(id);


        verify(eventService, times(1)).getById(id);
        verify(eventMapper, times(1)).toEntity(dto);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testGetIdFromEntity() {
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        Set<AbstractEvent> events = Sets.newLinkedHashSet(event);
        Set<Long> eventIds = Sets.newLinkedHashSet(1L);

        Set<Long> result = eventService.getIdFromEntity(events);

        assertEquals(eventIds, result);
    }

    @Test
    public void testValidateEventByActive(){
        Long id = 1L;
        CinemaEventOutcomeDto dto = new CinemaEventOutcomeDto();
        dto.setDate(1L);
        dto.setVisible(true);
        doReturn(dto).when(eventService).getById(id);
        doNothing().when(eventService).delete(id);

        eventService.validateEventByActive(id);

        verify(eventService, times(1)).getById(id);
        verify(eventService, times(1)).delete(id);
    }

    @Test
    public void testExistById(){
        Long id = 1L;
        when(eventRepository.existsById(id)).thenReturn(true);

        boolean result = eventService.existById(id);

        verify(eventRepository,times(1)).existsById(id);
        assertTrue(result);
    }
}