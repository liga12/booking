package com.booking.event.service.place;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.dto.event.CinemaEventOutcomeDto;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.service.event.EventService;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.transport.mapper.EventMapper;
import com.booking.event.transport.mapper.PlaceMapper;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PlaceServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceMapper placeMapper;

    @InjectMocks
    @Spy
    private PlaceServiceImpl placeService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventService eventService;

    @Test
    public void testGetAll() {
        Place place = new Place();
        PlaceOutcomeDto dto = new PlaceOutcomeDto();
        PageImpl<Place> page = new PageImpl(Lists.newArrayList(place));
        when(placeRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
                )
        ).thenReturn(page);
        when(placeMapper.toDto(place)).thenReturn(dto);

        Page<PlaceOutcomeDto> result = placeService.getAll(
                new PlaceFindDto(),
                PageRequest.of(0, 100)
        );

        verify(placeRepository, times(1)).findAll(
                any(Specification.class),
                any(Pageable.class)
        );

        verify(placeMapper, times(1)).toDto(place);
        assertEquals(page.map(placeMapper::toDto), result);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Place place = new Place();
        PlaceOutcomeDto dto = new PlaceOutcomeDto();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeMapper.toDto(place)).thenReturn(dto);

        PlaceOutcomeDto result = placeService.getById(id);

        verify(placeRepository, times(1)).findById(id);
        verify(placeMapper, times(1)).toDto(place);
        assertEquals(dto, result);
    }

    @Test
    public void testGetByIds() {
        Set<Long> ids = Sets.newHashSet(1L);
        Place place = new Place();
        place.setId(1L);
        when(placeRepository.findAllById(ids)).thenReturn(Lists.newArrayList(place));

        Set<Place> result = placeService.getById(ids);

        verify(placeRepository, times(1)).findAllById(ids);
        assertEquals(Sets.newHashSet(Lists.newArrayList(place)), result);
    }

    @Test
    public void testCreate() {
        PlaceCreateDto placeCreateDto = new PlaceCreateDto(1, 1, 1L, SectionType.FIRST_ROW);
        CinemaEventOutcomeDto eventOutcomeDto = new CinemaEventOutcomeDto();
        CinemaEvent event = new CinemaEvent();
        Place place = new Place();
        place.setId(1L);
        when(eventService.getById(placeCreateDto.getEvent())).thenReturn(eventOutcomeDto);
        when(eventMapper.toEntity(eventOutcomeDto)).thenReturn(event);
        when(placeRepository.existsAllByNumberAndAndRowAndSectionTypeAndEvent(
                placeCreateDto.getNumber(),
                placeCreateDto.getRow(),
                placeCreateDto.getSectionType(),
                event)
        ).thenReturn(false);
        doNothing().when(eventService).validateEventByActive(placeCreateDto.getEvent());
        when(placeMapper.toEntity(placeCreateDto)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);

        Long result = placeService.create(placeCreateDto);

        verify(eventService, times(1)).getById(placeCreateDto.getEvent());
        verify(eventMapper, times(1)).toEntity(eventOutcomeDto);
        verify(placeRepository, times(1)).existsAllByNumberAndAndRowAndSectionTypeAndEvent(
                placeCreateDto.getNumber(),
                placeCreateDto.getRow(),
                placeCreateDto.getSectionType(),
                event);
        verify(placeMapper, times(1)).toEntity(placeCreateDto);
        verify(placeRepository, times(1)).save(place);
        assertEquals(place.getId(), result);
    }

    @Test
    public void testUpdate() {
        PlaceUpdateDto placeUpdateDto = new PlaceUpdateDto();
        CinemaEventOutcomeDto eventOutcomeDto = new CinemaEventOutcomeDto();
        placeUpdateDto.setId(1L);
        Place place = new Place();
        place.setId(1L);
        when(eventService.getById(placeUpdateDto.getId())).thenReturn(eventOutcomeDto);
        doNothing().when(eventService).validateEventByActive(eventOutcomeDto.getId());
        when(placeMapper.toEntity(placeUpdateDto)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);

        Long result = placeService.update(placeUpdateDto);

        verify(eventService, times(1)).getById(placeUpdateDto.getId());
        verify(placeMapper, times(1)).toEntity(placeUpdateDto);
        verify(placeRepository, times(1)).save(place);
        assertEquals(place.getId(), result);
    }

    @Test
    public void testBuyPlace(){
        Long id = 1L;
        PlaceOutcomeDto placeOutcomeDto = new PlaceOutcomeDto();
        Place place = new Place();
        doReturn(placeOutcomeDto).when(placeService).getById(id);
        when(placeMapper.toEntity(placeOutcomeDto)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);

        placeService.buyPlace(id);

        verify(placeService, times(1)).getById(id);
        verify(placeMapper, times(1)).toEntity(placeOutcomeDto);
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    public void testDelete(){
        Long id = 1L;
        PlaceOutcomeDto placeOutcomeDto = new PlaceOutcomeDto();
        Place place = new Place();
        doReturn(placeOutcomeDto).when(placeService).getById(id);
        when(placeMapper.toEntity(placeOutcomeDto)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);

        placeService.delete(id);

        verify(placeService, times(1)).getById(id);
        verify(placeMapper, times(1)).toEntity(placeOutcomeDto);
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    public void testGetIdFromEntity(){
        Place place = new Place();
        place.setId(1L);
        Set<Place> places = Sets.newHashSet(place);

        Set<Long> result = placeService.getIdFromEntity(places);

        assertEquals(Sets.newHashSet(1L), result);
    }

    @Test
    public void testExistActivePlace(){
        Long id = 1L;
        when(placeRepository.existsByIdAndStatus(id, PlaceStatusType.ACTIVE)).thenReturn(true);

        boolean result = placeService.existActivePlace(id);

        verify(placeRepository, times(1)).existsByIdAndStatus(id, PlaceStatusType.ACTIVE);
        assertTrue(result);
    }

    @Test
    public void testExistBuyPlace(){
        Long id = 1L;
        when(placeRepository.existsByIdAndStatus(id, PlaceStatusType.BYU)).thenReturn(true);

        boolean result = placeService.existBuyPlace(id);

        verify(placeRepository, times(1)).existsByIdAndStatus(id, PlaceStatusType.BYU);
        assertTrue(result);
    }
}