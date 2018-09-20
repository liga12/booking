package com.booking.event.service.place;

import com.booking.event.dto.PlaceOutcomeDto;
import com.booking.event.exception.EventNotActiveException;
import com.booking.event.exception.EventNotFoundException;
import com.booking.event.exception.PlaceExistException;
import com.booking.event.exception.PlaceNotFoundException;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.persistence.repository.PlaceRepository;
import com.booking.event.transport.dto.place.PlaceCreateDto;
import com.booking.event.transport.dto.place.PlaceFindDto;
import com.booking.event.transport.dto.place.PlaceUpdateDto;
import com.booking.event.transport.mapper.PlaceMapper;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
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
import static com.booking.event.asserts.Asserts.assertEqualPlacesAndDtos;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlaceServiceImplIntegrationTest {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<PlaceOutcomeDto> result = placeService.getAll(new PlaceFindDto(), pageable);

        assertEqualPlacesAndDtos(placeRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setId(1L);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByName() {
        Place place = placeRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setPrice(160d);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByNumber() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setNumber(1);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByRow() {
        Place place = placeRepository.findById(3L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setRow(51);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByStatus() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(2L).orElseThrow(AssertionError::new);
        Place place3 = placeRepository.findById(4L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setStatus(PlaceStatusType.ACTIVE);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place, place2, place3), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEvent() {
        Place place = placeRepository.findById(3L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(4L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setEvent(2L);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place, place2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllBySectionType() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        Place place2 = placeRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setSectionType(SectionType.FIRST_ROW);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place, place2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAll() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        PlaceFindDto dto = new PlaceFindDto();
        dto.setSectionType(SectionType.FIRST_ROW);
        dto.setStatus(PlaceStatusType.ACTIVE);
        dto.setNumber(1);
        dto.setId(1L);

        Page<PlaceOutcomeDto> result = placeService.getAll(dto, pageable);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() {
        Place place = placeRepository.findById(1L).orElseThrow(AssertionError::new);

        PlaceOutcomeDto result = placeService.getById(1L);

        assertEqualPlacesAndDtos(Lists.newArrayList(place), Lists.newArrayList(result));
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithPlaceNotFoundException() {
        placeService.getById(100L);
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithIdNull() {
        Long id = null;
        placeService.getById(id);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetByIds() {
        PlaceOutcomeDto place = placeMapper.toDto(placeRepository.findById(1L).orElseThrow(AssertionError::new));

        Set<Place> result = placeService.getById(Sets.newLinkedHashSet(1L));

        assertEqualPlacesAndDtos(Lists.newArrayList(result), Lists.newArrayList(place));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetByIdsNull() {
        Set<Long> ids = null;

        assertNull(placeService.getById(ids));
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdsWithEventNotFoundExceptionSecond() {
        placeService.getById(Sets.newLinkedHashSet(1L, 20L));
    }

    @Test
    @Sql("/scripts/create.sql")
    public void testCreate() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                SectionType.FIRST_ROW
        );

        Long result = placeService.create(dto);

        assertEquals(placeService.getById(result).getId(), result);
    }

    @Test(expected = EventNotFoundException.class)
    public void testCreateWithEventNull() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                1,
                null,
                SectionType.FIRST_ROW
        );

        placeService.create(dto);
    }

    @Test(expected = EventNotActiveException.class)
    @Sql("/scripts/create.sql")
    public void testCreateWithEventNotActiveException() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                1,
                2L,
                SectionType.FIRST_ROW
        );

        placeService.create(dto);
    }

    @Test(expected = PlaceExistException.class)
    @Sql("/scripts/create.sql")
    public void testCreateWithNumberNull() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                null,
                1,
                1L,
                SectionType.FIRST_ROW
        );

        placeService.create(dto);
    }

    @Test(expected = PlaceExistException.class)
    @Sql("/scripts/create.sql")
    public void testCreateWithRowNull() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                null,
                1L,
                SectionType.FIRST_ROW
        );

        placeService.create(dto);
    }

    @Test(expected = PlaceExistException.class)
    @Sql("/scripts/create.sql")
    public void testCreateWithTypeNull() {
        PlaceCreateDto dto = new PlaceCreateDto(
                10D,
                1,
                1,
                1L,
                null
        );

        placeService.create(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdate() {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );

        Long result = placeService.update(dto);

        assertEquals(dto.getId(), result);
        assertEqualDtoAndDto(dto, placeService.getById(dto.getId()));
    }


    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithIdNull() {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                null,
                10D,
                PlaceStatusType.ACTIVE
        );

        placeService.update(dto);
    }

    @Test(expected = PlaceNotFoundException.class)
    public void testUpdateWithPlaceNotFoundException() {
        PlaceUpdateDto dto = new PlaceUpdateDto(
                1L,
                10D,
                PlaceStatusType.ACTIVE
        );

        placeService.update(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testBuyPlace() {
        Long id = 1L;

        placeService.buyPlace(id);

        assertEquals(PlaceStatusType.BYU, placeService.getById(id).getStatus());
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testBuyPlaceWithNotActiveStatus() {
        placeService.buyPlace(3L);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDelete() {
        Long id = 1L;

        placeService.delete(1L);

        assertEquals(PlaceStatusType.NOT_ACTIVE, placeService.getById(id).getStatus());
    }

    @Test(expected = PlaceNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testDeleteWithIdNull() {
        placeService.delete(null);
    }

    @Test(expected = PlaceNotFoundException.class)
    public void testDeleteWithPlaceNotFoundException() {
        placeService.delete(1L);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetIdFromEntity() {
        Place place = placeMapper.toEntity(placeService.getById(1L));
        Place place2 = placeMapper.toEntity(placeService.getById(2L));
        Set<Long> ids = Sets.newLinkedHashSet(place.getId(), place2.getId());

        Set<Long> result = placeService.getIdFromEntity(Sets.newLinkedHashSet(place, place2));

        Iterator<Long> idsIterator = ids.iterator();
        Iterator<Long> resultIterator = result.iterator();
        assertEquals(ids.size(), result.size());
        assertEquals(idsIterator.next(), resultIterator.next());
        assertEquals(idsIterator.next(), resultIterator.next());
    }

    @Test
    public void testGetIdFromEntityPlacesNull() {

        assertNull(placeService.getIdFromEntity(null));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistActivePlace() {
        assertTrue(placeService.existActivePlace(1L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistActivePlaceFalse() {
        assertFalse(placeService.existActivePlace(3L));
    }

    @Test
    public void testExistActivePlaceIdNull() {
        assertFalse(placeService.existActivePlace(1L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistBuyPlace() {
        assertTrue(placeService.existBuyPlace(3L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExistBuyPlaceFalse() {
        assertFalse(placeService.existBuyPlace(1L));
    }

    @Test
    public void testExistBuyPlaceIdNull() {
        assertFalse(placeService.existBuyPlace(1L));
    }
}