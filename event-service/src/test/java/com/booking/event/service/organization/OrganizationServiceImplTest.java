package com.booking.event.service.organization;

import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.event.CinemaEvent;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.service.event.EventService;
import com.booking.event.service.feign.UserService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import com.booking.event.transport.mapper.OrganizationMapper;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    @Spy
    private OrganizationServiceImpl organizationService;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @Test
    public void testGetAll() {
        Organization organization = new Organization();
        OrganizationOutcomeDto dto = new OrganizationOutcomeDto();
        PageImpl<Organization> page = new PageImpl(Lists.newArrayList(organization));
        when(organizationRepository.findAll(
                any(Specification.class),
                any(Pageable.class)
                )
        ).thenReturn(page);
        when(organizationMapper.toDto(organization)).thenReturn(dto);

        Page<OrganizationOutcomeDto> result = organizationService.getAll(
                new OrganizationFindDto(),
                PageRequest.of(0, 100)
        );

        verify(organizationRepository, times(1)).findAll(
                any(Specification.class),
                any(Pageable.class)
        );

        verify(organizationMapper, times(1)).toDto(organization);
        assertEquals(page.map(organizationMapper::toDto), result);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Organization organization = new Organization();
        OrganizationOutcomeDto dto = new OrganizationOutcomeDto();
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));
        when(organizationMapper.toDto(organization)).thenReturn(dto);

        OrganizationOutcomeDto result = organizationService.getById(id);

        verify(organizationRepository, times(1)).findById(id);
        verify(organizationMapper, times(1)).toDto(organization);
        assertEquals(dto, result);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testGetByIdNull() {
        organizationService.getById(null);

    }

    @Test
    public void testCreate() {
        OrganizationCreateDto dto = new OrganizationCreateDto();
        dto.setCustomerId("1");
        dto.setName("name");
        Organization organization = new Organization(1L);
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(true);
        when(organizationRepository.existsByName(dto.getName())).thenReturn(false);
        when(organizationMapper.toEntity(dto)).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);

        Long result = organizationService.create(dto);

        verify(userService, times(1)).existsCustomer(dto.getCustomerId());
        verify(organizationRepository, times(1)).existsByName(dto.getName());
        assertEquals(organization.getId(), result);
    }

    @Test
    public void testUpdate() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(1L);
        dto.setName("name");
        Organization organization = new Organization(1L);
        when(organizationRepository.existsByNameAndIdIsNot(dto.getName(), dto.getId())).thenReturn(false);
        when(organizationMapper.toEntity(dto)).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);

        Long result = organizationService.update(dto);

        verify(organizationRepository, times(1)).existsByNameAndIdIsNot(dto.getName(), dto.getId());
        verify(organizationMapper, times(1)).toEntity(dto);
        when(organizationRepository.save(organization)).thenReturn(organization);
        assertEquals(organization.getId(), result);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testUpdateWithDtoNull() {
        organizationService.update(null);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testUpdateWithIdNull() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto();
        organizationService.update(dto);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        OrganizationOutcomeDto dto = new OrganizationOutcomeDto();
        Organization organization = new Organization();
        CinemaEvent event = new CinemaEvent();
        event.setId(1L);
        organization.setEvents(Sets.newLinkedHashSet(event));
        doReturn(dto).when(organizationService).getById(id);
        when(organizationMapper.toEntity(dto)).thenReturn(organization);
        doNothing().when(eventService).delete(anyLong());
        when(organizationRepository.save(organization)).thenReturn(organization);

        organizationService.delete(id);

        verify(organizationService, times(1)).getById(id);
        verify(organizationMapper, times(1)).toEntity(dto);
        verify(organizationRepository, times(1)).save(organization);
    }

    @Test
    public void testExists() {
        Long id = 1L;
        when(organizationRepository.existsById(id)).thenReturn(true);

        boolean result = organizationService.exists(id);

        verify(organizationRepository, times(1)).existsById(id);
        assertTrue(result);
    }
}