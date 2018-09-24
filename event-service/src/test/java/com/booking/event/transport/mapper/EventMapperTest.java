package com.booking.event.transport.mapper;

import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.entity.event.CoverConcertEvent;
import com.booking.event.persistence.entity.event.OriginalConcertEvent;
import com.booking.event.persistence.entity.event.TheatreEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.service.organization.OrganizationService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import com.booking.event.transport.dto.event.ageConstrain.theatre.TheatreEventCreateDto;
import com.booking.event.transport.dto.event.coverConcert.CoverConcertEventCreateDto;
import com.booking.event.transport.dto.event.originalConcert.OriginalConcertEventCreateDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EventMapperTest {

    @InjectMocks
    private EventMapperImpl eventMapper;

    @Mock
    private OrganizationMapper organizationMapper;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private PlaceService placeService;

    @Test
    public void testOriginalConcertEventCreateDtoToEntity() {
        OriginalConcertEventCreateDto dto = new OriginalConcertEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        OriginalConcertEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
    }

    @Test
    public void testOriginalConcertEventCreateDtoToEntityNull() {
        OriginalConcertEventCreateDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }

    @Test
    public void testCoverConcertEventCreateDtoToEntity() {
        CoverConcertEventCreateDto dto = new CoverConcertEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        CoverConcertEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
    }

    @Test
    public void testCoverConcertEventCreateDtoToEntityNull() {
        CoverConcertEventCreateDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }

    @Test
    public void testCinemaEventCreateDtoToEntity() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        CinemaEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
        assertEquals(dto.getMinAge(), result.getMinAge());
    }

    @Test
    public void testCinemaEventCreateDtoToEntityNull() {
        CinemaEventCreateDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }

    @Test
    public void testTheatreEventCreateDtoToEntity() {
        TheatreEventCreateDto dto = new TheatreEventCreateDto();
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        TheatreEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
        assertEquals(dto.getMinAge(), result.getMinAge());
    }

    @Test
    public void testTheatreEventCreateDtoToEntityNull() {
        TheatreEventCreateDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }


    @Test
    public void testAbstractEventOutcomeDtoToEntity() {
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
        Place place = new Place(1L);
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(placeService.getById(dto.getPlaces())).thenReturn(Sets.newLinkedHashSet(place));
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        CinemaEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
        assertEquals(dto.getMinAge(), result.getMinAge());
        assertEquals(dto.getPlaces().size(), result.getPlaces().size());
        assertEquals(dto.getPlaces().iterator().next(), result.getPlaces().iterator().next().getId());
    }

    @Test
    public void testAbstractEventOutcomeDtoToEntityNull() {
        CinemaEventOutcomeDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }

    @Test
    public void testAbstractEventUpdateDtoToEntity() {
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
        OrganizationOutcomeDto organizationOutcomeDto = new OrganizationOutcomeDto(dto.getOrganization());
        Organization organization = new Organization(dto.getOrganization());
        when(organizationService.getById(dto.getOrganization())).thenReturn(organizationOutcomeDto);
        when(organizationMapper.toEntity(organizationOutcomeDto)).thenReturn(organization);

        CinemaEvent result = eventMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getLocation(), result.getLocation());
        assertEquals(dto.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(dto.getArtists(), result.getArtists());
        assertEquals(dto.getOrganization(), result.getOrganization().getId());
        assertEquals(dto.getMinAge(), result.getMinAge());
    }

    @Test
    public void testAbstractEventUpdateDtoToEntityNull() {
        CinemaEventUpdateDto dto = null;

        assertNull(eventMapper.toEntity(dto));
    }

    @Test
    public void testToDto() {
        Organization organization = new Organization(1L);
        Place place = new Place(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        event.setName("name");
        event.setDescription("desc");
        event.setDate(1L);
        event.setLocation("loc");
        event.setPhotoUrl("photo");
        event.setArtists("artist");
        event.setOrganization(organization);
        event.setMinAge(12);
        event.setPlaces(Sets.newLinkedHashSet(place));
        when(placeService.getIdFromEntity(event.getPlaces()))
                .thenReturn(
                        Sets.newLinkedHashSet(
                                event.getPlaces()
                                        .iterator()
                                        .next()
                                        .getId()
                        )
                );

        CinemaEventOutcomeDto result = eventMapper.toDto(event);

        assertEquals(event.getId(), result.getId());
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getDescription(), result.getDescription());
        assertEquals(event.getDate(), result.getDate());
        assertEquals(event.getLocation(), result.getLocation());
        assertEquals(event.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(event.getArtists(), result.getArtists());
        assertEquals(event.getOrganization().getId(), result.getOrganization());
        assertEquals(event.getMinAge(), result.getMinAge());
        assertEquals(event.getPlaces().size(), result.getPlaces().size());
        assertEquals(event.getPlaces().iterator().next().getId(), result.getPlaces().iterator().next());
    }

    @Test
    public void testToDtoNull() {
        CinemaEvent event = null;

        assertNull(eventMapper.toDto(event));
    }
}