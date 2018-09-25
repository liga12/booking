package com.booking.event.transport.mapper;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.AbstractEventOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.service.event.EventService;
import com.booking.event.service.place.PlaceService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
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
public class PlaceMapperTest {

    @InjectMocks
    private PlaceMapperImpl placeMapper;

    @Mock
    private EventService eventService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private PlaceService placeService;

    @Test
    public void testPlaceCreateDtoToEntity() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );
        AbstractEventOutcomeDto cinemaEventOutcomeDto = new CinemaEventOutcomeDto();
        cinemaEventOutcomeDto.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        when(eventService.getById(dto.getEvent())).thenReturn(cinemaEventOutcomeDto);
        when(eventMapper.toEntity(cinemaEventOutcomeDto)).thenReturn(event);

        Place result = placeMapper.toEntity(dto);

        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getNumber(), result.getNumber());
        assertEquals(dto.getRow(), result.getRow());
        assertEquals(dto.getEvent(), result.getEvent().getId());
    }

    @Test
    public void testPlaceCreateDtoToEntityNull() {
        PlaceCreateDto dto = null;

        assertNull(placeMapper.toEntity(dto));
    }

    @Test
    public void testPlaceOutcomeDtoToEntity() {
        PlaceOutcomeDto dto = new PlaceOutcomeDto(
                1L,
                10D,
                1,
                1,
                PlaceStatusType.ACTIVE,
                1L,
                SectionType.FIRST_ROW
        );
        AbstractEventOutcomeDto cinemaEventOutcomeDto = new CinemaEventOutcomeDto();
        cinemaEventOutcomeDto.setId(1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        when(eventService.getById(dto.getEvent())).thenReturn(cinemaEventOutcomeDto);
        when(eventMapper.toEntity(cinemaEventOutcomeDto)).thenReturn(event);

        Place result = placeMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getNumber(), result.getNumber());
        assertEquals(dto.getRow(), result.getRow());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getEvent(), result.getEvent().getId());
    }

    @Test
    public void testPlaceOutcomeDtoToEntityNull() {
        PlaceOutcomeDto dto = null;

        assertNull(placeMapper.toEntity(dto));
    }

    @Test
    public void testPlaceUpdateDtoToEntity() {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );
        AbstractEventOutcomeDto cinemaEventOutcomeDto = new CinemaEventOutcomeDto();
        cinemaEventOutcomeDto.setId(1L);
        PlaceOutcomeDto placeOutcomeDto = new PlaceOutcomeDto(1, 1, 1L);
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        when(placeService.getById(dto.getId())).thenReturn(placeOutcomeDto);
        when(placeService.getById(dto.getId())).thenReturn(placeOutcomeDto);
        when(eventService.getById(placeOutcomeDto.getEvent())).thenReturn(cinemaEventOutcomeDto);
        when(eventMapper.toEntity(cinemaEventOutcomeDto)).thenReturn(event);

        Place result = placeMapper.toEntity(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getStatus(), result.getStatus());
    }

    @Test
    public void testPlaceUpdateDtoToEntityNull() {
        PlaceUpdateDto dto = null;

        assertNull(placeMapper.toEntity(dto));
    }

    @Test
    public void testToDto() {
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        Place place = new Place(
                1L,
                1D,
                1,
                1,
                PlaceStatusType.ACTIVE,
                event,
                SectionType.FIRST_ROW
        );

        PlaceOutcomeDto result = placeMapper.toDto(place);

        assertEquals(place.getId(), result.getId());
        assertEquals(place.getPrice(), result.getPrice());
        assertEquals(place.getNumber(), result.getNumber());
        assertEquals(place.getRow(), result.getRow());
        assertEquals(place.getStatus(), result.getStatus());
        assertEquals(place.getEvent().getId(), result.getEvent());
        assertEquals(place.getSectionType(), result.getSectionType());
    }

    @Test
    public void testToDtoNull() {
        Place place = null;

        assertNull(placeMapper.toDto(place));
    }
}