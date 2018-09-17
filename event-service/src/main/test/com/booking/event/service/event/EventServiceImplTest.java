package com.booking.event.service.event;

import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.EventRepository;
import com.booking.event.transport.dto.event.EventFindDto;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    @Spy
    private EventServiceImpl eventService;

    @Test
    public void tetGetAll() {
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

        eventService.getAll(
                new EventFindDto(),
                PageRequest.of(0, 100)
        );
    }

}