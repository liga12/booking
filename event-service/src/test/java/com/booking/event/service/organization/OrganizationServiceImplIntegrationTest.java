package com.booking.event.service.organization;

import com.booking.event.exception.CustomerNotFoundException;
import com.booking.event.exception.OrganizationNameExistException;
import com.booking.event.exception.OrganizationNotActiveException;
import com.booking.event.exception.OrganizationNotFoundException;
import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.service.event.EventService;
import com.booking.event.service.feign.UserService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationFindDto;
import com.booking.event.transport.dto.organization.OrganizationOutcomeDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.booking.event.asserts.Asserts.assertEqualDtoAndDto;
import static com.booking.event.asserts.Asserts.assertEqualOrganizationsAndDtos;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrganizationServiceImplIntegrationTest {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<OrganizationOutcomeDto> result = organizationService.getAll(new OrganizationFindDto(), pageable);

        assertEqualOrganizationsAndDtos(organizationRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() {
        Long id = 1L;
        Organization organization = organizationRepository.findById(id).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setId(id);

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByName() {
        Organization organization = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(3L).orElseThrow(AssertionError::new);
        Organization organization3 = organizationRepository.findById(6L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setName("45");

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(
                Lists.newArrayList(organization, organization2, organization3),
                result.getContent()
        );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByLocation() {
        Organization organization = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(4L).orElseThrow(AssertionError::new);
        Organization organization3 = organizationRepository.findById(5L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setLocation("4");

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(
                Lists.newArrayList(organization, organization2, organization3),
                result.getContent()
        );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPhone() {
        Organization organization = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(4L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setPhone("31");

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization, organization2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEmail() {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(5L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setEmail("2");

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization, organization2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByVisible() {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        Organization organization3 = organizationRepository.findById(3L).orElseThrow(AssertionError::new);
        Organization organization4 = organizationRepository.findById(4L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setVisible(true);

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(
                Lists.newArrayList(organization, organization2, organization3, organization4),
                result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEvents() {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setEvents(Sets.newLinkedHashSet(1L, 3L));

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization, organization2), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAll() {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        OrganizationFindDto dto = new OrganizationFindDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setVisible(true);

        Page<OrganizationOutcomeDto> result = organizationService.getAll(dto, pageable);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization), result.getContent());
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);

        OrganizationOutcomeDto result = organizationService.getById(1L);

        assertEqualOrganizationsAndDtos(Lists.newArrayList(organization), Lists.newArrayList(result));
    }

    @Test(expected = OrganizationNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithOrganizationNotFoundException() {
        organizationService.getById(100L);
    }

    @Test(expected = OrganizationNotFoundException.class)
    @Sql("/scripts/init.sql")
    public void testGetByIdWithIdNull() {
        Long id = null;
        organizationService.getById(id);
    }

    @Test
    public void testCreate() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111",
                "email",
                "1"
        );
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(true);

        Long result = organizationService.create(dto);

        assertEquals(organizationService.getById(result).getId(), result);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testCreateWithCustomerNotFoundException() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111",
                "email",
                "1"
        );
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(false);

        organizationService.create(dto);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testCreateWithCustomerIdNull() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111",
                "email",
                null
        );
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(true);

        organizationService.create(dto);
    }

    @Test(expected = OrganizationNameExistException.class)
    public void testCreateWithOrganizationNameExistException() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111",
                "email",
                "1"
        );
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(true);

        organizationService.create(dto);
        organizationService.create(dto);
    }

    @Test(expected = OrganizationNameExistException.class)
    public void testCreateWithNameNull() {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                null,
                "loc",
                "111",
                "email",
                "1"
        );
        when(userService.existsCustomer(dto.getCustomerId())).thenReturn(true);

        organizationService.create(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testUpdate() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name456",
                "loc",
                "111",
                "email"
        );

        Long result = organizationService.update(dto);

        assertEquals(dto.getId(), result);
        assertEqualDtoAndDto(dto, organizationService.getById(dto.getId()));
    }

    @Test(expected = OrganizationNameExistException.class)
    @Sql("/scripts/init.sql")
    public void testUpdateWithOrganizationNameExistException() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name4546",
                "loc",
                "111",
                "email"
        );

        organizationService.update(dto);
    }

    @Test(expected = OrganizationNameExistException.class)
    public void testUpdateWithOrganizationNameNull() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                null,
                "loc",
                "111",
                "email"
        );

        organizationService.update(dto);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testUpdateWithOrganizationIdNull() {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                null,
                "dsfsd",
                "loc",
                "111",
                "email"
        );

        organizationService.update(dto);
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDelete() {
        Long id = 1L;

        organizationService.delete(id);

        OrganizationOutcomeDto organization = organizationService.getById(id);
        assertFalse(organization.getVisible());
        for (Long place : organization.getEvents()) {
            assertFalse(eventService.getById(place).getVisible());
        }
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testDeleteEventNull() {
        Long id = 6L;

        organizationService.delete(id);

        OrganizationOutcomeDto organization = organizationService.getById(id);
        assertFalse(organization.getVisible());
        for (Long place : organization.getEvents()) {
            assertFalse(eventService.getById(place).getVisible());
        }
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testExists() {
        assertTrue(organizationService.exists(1L));
    }

    @Test
    public void testExistsFalse() {
        assertFalse(organizationService.exists(1L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testValidateOrganizationByActive() {
        organizationService.validateOrganizationByActive(1L);

    }

    @Test(expected = OrganizationNotActiveException.class)
    public void testValidateOrganizationByActiveWithIdNull() {
        organizationService.validateOrganizationByActive(null);
    }

    @Test(expected = OrganizationNotFoundException.class)
    public void testValidateOrganizationByActiveWithOrganizationNotFoundException() {
        organizationService.validateOrganizationByActive(1L);
    }

    @Test(expected = OrganizationNotActiveException.class)
    @Sql("/scripts/init.sql")
    public void testValidateOrganizationByActiveWithOrganizationNotActive() {
        organizationService.validateOrganizationByActive(6L);
    }
}