package com.booking.event.service.event;

import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.transport.dto.event.EventFindDto;
import com.booking.event.type.EventType;
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

import static com.booking.event.asserts.Asserts.assertEqualEventAndDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EventServiceImplImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto event = new EventFindDto();

        Page<AbstractEventOutcomeDto> result = eventService.getAll(event, pageable);

        assertEqualEventAndDto(eventRepository.findAll(), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2, event3), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2, event3), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
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

        assertEqualEventAndDto(Lists.newArrayList(event, event2, event3), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPlaces() {
        AbstractEvent event = eventRepository.findById(1L).orElseThrow(AssertionError::new);
        AbstractEvent event2 = eventRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        EventFindDto dto = new EventFindDto();
        dto.setPlaces(Sets.newLinkedHashSet(1L,3L));

        Page<AbstractEventOutcomeDto> result = eventService.getAll(dto, pageable);

        assertEqualEventAndDto(Lists.newArrayList(event, event2), result.getContent());
    }
}