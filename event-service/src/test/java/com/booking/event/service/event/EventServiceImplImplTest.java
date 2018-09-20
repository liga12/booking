package com.booking.event.service.event;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.exception.EventNotFoundException;
import com.booking.event.exception.NotCorrectDateException;
import com.booking.event.exception.OrganizationNotActiveException;
import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventCreateDto;
import com.booking.event.transport.dto.event.ageConstrain.cinema.CinemaEventUpdateDto;
import com.booking.event.transport.mapper.EventMapper;
import com.booking.event.type.EventType;
import com.booking.event.type.PlaceStatusType;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Set;

import static com.booking.event.asserts.Asserts.assertEqualDtoAndDto;
import static com.booking.event.asserts.Asserts.assertEqualEventsAndDtos;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EventServiceImplImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PlaceService placeService;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(new EventFindDto(), pageable);

        assertEqualEventsAndDtos(eventRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() {
        Long id = 1L;
        AbstractEvent event = eventRepository.findById(id).orElseThrow(AssertionError::new);

        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setId(id);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByName() {
        AbstractEvent event = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        AbstractEvent event3 = eventRepository.findById(4L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setName("21");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2, event3), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByType() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event3 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setType(EventType.CINEMA);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2, event3), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByDate() {
        AbstractEvent event = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setStarDate(2L);
        dto.setEndDate(3L);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByDescription() {
        AbstractEvent event = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setDescription("2");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByLocation() {
        AbstractEvent event = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setLocation("2");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByOrganization() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setOrganization(1L);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByArtists() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setArtists("2");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByVisible() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        AbstractEvent event3 = eventRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setVisible(true);

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2, event3), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPlaces() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setPlaces(Sets.newLinkedHashSet(1L, 3L));

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event, event2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAll() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setId(1L);
        dto.setVisible(true);
        dto.setArtists("2a");
        dto.setLocation("loc1");

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventsAndDtos(Lists.newArrayList(event), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);

        AbstractEventOutcomeDto result = eventService.getById(1L);

        assertEqualEventsAndDtos(Lists.newArrayList(event), Lists.newArrayList(result));
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithEventNotFoundException() {
        eventService.getById(100L);
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithIdNull() {
        Long id = null;
        eventService.getById(id);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetByIds() {
        AbstractEventOutcomeDto event = eventMapper.toDto(eventRepository.findById(1L).orElseThrow(AssertionError::new));
        AbstractEventOutcomeDto event2 = eventMapper.toDto(eventRepository.findById(2L).orElseThrow(AssertionError::new));

        Set<AbstractEvent> result = eventService.getById(Sets.newLinkedHashSet(1L, 2L));

        assertEqualEventsAndDtos(Lists.newArrayList(result), Lists.newArrayList(event, event2));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetByIdsNull() {
        Set<Long> ids = null;

        assertNull(eventService.getById(ids));
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdsWithEventNotFoundExceptionSecond() {
        eventService.getById(Sets.newLinkedHashSet(1L, 20L));
    }

    @Test
    @Sql("/scripts/createEvent.sql")
    public void testCreate() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("dedfbsc");
        dto.setDate(199999999999L);
        dto.setLocation("yukyuk");
        dto.setPhotoUrl("phoyukyto");
        dto.setArtists("fddfgdfg");
        dto.setOrganization(1L);
        dto.setMinAge(14);

        Long result = eventService.create(dto);

        assertEquals(eventService.getById(result).getId(), result);
    }

    @Test(expected = NotCorrectDateException.class)
    public void testCreateWithNotCorrectDateException() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("desc");
        dto.setDate(1L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.create(dto);
    }

    @Test(expected = NotCorrectDateException.class)
    public void testCreateWithDateNull() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("try");
        dto.setDate(null);
        dto.setLocation("lotryc");
        dto.setPhotoUrl("photyo");
        dto.setArtists("arttrytrist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.create(dto);
    }

    @Test(expected = OrganizationNotActiveException.class)
    public void testCreateWitOrganizationNotActiveException() {
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("desc");
        dto.setDate(1777777777777777L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(null);
        dto.setMinAge(12);

        eventService.create(dto);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testCreateWitOrganizationNotFoundException() {

        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("desc");
        dto.setDate(1777777777777777L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.create(dto);
    }

    @Test(expected = OrganizationNotActiveException.class)
    public void testCreateWitOrganizationNotActive() {
        Organization organization = new Organization(
                1L,
                "name",
                "loc",
                "1111",
                "email",
                false,
                null,
                "1");
        organizationRepository.save(organization);
        CinemaEventCreateDto dto = new CinemaEventCreateDto();
        dto.setName("name45345");
        dto.setDescription("desc");
        dto.setDate(1777777777777777L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.create(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdate() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(178888888888888L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        Long result = eventService.update(dto);

        assertEquals(dto.getId(), result);
        assertEqualDtoAndDto(dto, eventService.getById(dto.getId()));
    }

    @Test(expected = NotCorrectDateException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithNotCorrectDateException() {
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

        eventService.update(dto);
    }

    @Test(expected = NotCorrectDateException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithDateNull() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(null);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithIdNull() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(null);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(1999999999L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithEventNotFoundException() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(20L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(19999999999999L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(1L);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWitOrganizationNotActiveException() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(20L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(19999999999999L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(null);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test(expected = OrganizationNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWitOrganizationNotFoundException() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(19999999999999L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(20L);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test(expected = OrganizationNotActiveException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWitOrganizationNotActive() {
        CinemaEventUpdateDto dto = new CinemaEventUpdateDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setDescription("desc");
        dto.setDate(19999999999999L);
        dto.setLocation("loc");
        dto.setPhotoUrl("photo");
        dto.setArtists("artist");
        dto.setOrganization(5L);
        dto.setMinAge(12);

        eventService.update(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDelete() {
        Long id = 1L;

        eventService.delete(id);

        AbstractEventOutcomeDto event = eventService.getById(id);
        assertFalse(event.getVisible());
        for (Long place : event.getPlaces()) {
            assertEquals(PlaceStatusType.NOT_ACTIVE, placeService.getById(place).getStatus());
        }
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDeletePlaceNull() {
        Long id = 4L;

        eventService.delete(id);

        AbstractEventOutcomeDto event = eventService.getById(id);
        assertFalse(event.getVisible());
        for (Long place : event.getPlaces()) {
            assertEquals(PlaceStatusType.NOT_ACTIVE, placeService.getById(place).getStatus());
        }
    }

    @Test(expected = EventNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testDeleteIdNull() {
        Long id = null;

        eventService.delete(id);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetIdFromEntity() {
        AbstractEvent event = eventMapper.toEntity(eventService.getById(1L));
        AbstractEvent event2 = eventMapper.toEntity(eventService.getById(2L));
        Set<Long> ids = Sets.newLinkedHashSet(event.getId(), event2.getId());

        Set<Long> result = eventService.getIdFromEntity(Sets.newLinkedHashSet(event, event2));

        Iterator<Long> idsIterator = ids.iterator();
        Iterator<Long> resultIterator = result.iterator();
        assertEquals(ids.size(), result.size());
        assertEquals(idsIterator.next(), resultIterator.next());
        assertEquals(idsIterator.next(), resultIterator.next());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testValidateEventByActive() {
        Long id = 1L;

        eventService.validateEventByActive(id);

        assertFalse(eventService.getById(id).getVisible());
    }

    @Test(expected = EventNotFoundException.class)
    public void testValidateEventByActiveWithEventNotFoundException() {
        eventService.validateEventByActive(1L);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistById() {
        assertTrue(eventService.existById(1L));
    }

    @Test(expected = EventNotFoundException.class)
    public void testExistByIdNull() {
        eventService.existById(null);
    }

    @Test
    public void testExistByIdFalse() {
        assertFalse(eventService.existById(1L));
    }
}