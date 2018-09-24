package com.booking.event.controller;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.repository.OrganizationRepository;
import com.booking.event.service.feign.UserService;
import com.booking.event.transport.dto.organization.OrganizationCreateDto;
import com.booking.event.transport.dto.organization.OrganizationUpdateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.booking.event.util.Converter.mapToJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrganizationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganizationRepository organizationRepository;

    @MockBean
    private UserService userService;

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAll() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()))
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].events[0]").value(3L))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllById() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByName() throws Exception {
        Organization organization = organizationRepository.findById(2L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&name=45")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(3L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()))
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].events[0]").value(4L))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByLocation() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&location=loc")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()))
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].events[0]").value(3L))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByPhone() throws Exception {
        Organization organization = organizationRepository.findById(3L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&phone=1151")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(4L));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEmail() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&email=email1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByVisible() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&email=email1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()))
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].events[0]").value(3L))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByEvent() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&event=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].events[0]").value(1L))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByCustomer() throws Exception {
        Organization organization2 = organizationRepository.findById(2L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&customerId=2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].events[0]").value(3L))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetAllByAllAndPageable() throws Exception {
        Organization organization = organizationRepository.findById(6L).orElseThrow(AssertionError::new);
        Organization organization2 = organizationRepository.findById(5L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations?page=0&size=2&name=n&location=l&sort=id,DESC")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(organization.getId()))
                .andExpect(jsonPath("$.content[0].name").value(organization.getName()))
                .andExpect(jsonPath("$.content[0].location").value(organization.getLocation()))
                .andExpect(jsonPath("$.content[0].phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(organization.getEmail()))
                .andExpect(jsonPath("$.content[0].visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.content[0].customerId").value(organization.getCustomerId()))
                .andExpect(jsonPath("$.content[1].id").value(organization2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(organization2.getName()))
                .andExpect(jsonPath("$.content[1].location").value(organization2.getLocation()))
                .andExpect(jsonPath("$.content[1].phone").value(organization2.getPhone()))
                .andExpect(jsonPath("$.content[1].email").value(organization2.getEmail()))
                .andExpect(jsonPath("$.content[1].visible").value(organization2.getVisible()))
                .andExpect(jsonPath("$.content[1].customerId").value(organization2.getCustomerId()));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void testGetById() throws Exception {
        Organization organization = organizationRepository.findById(1L).orElseThrow(AssertionError::new);

        mockMvc.perform(get("/organizations/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(organization.getId()))
                .andExpect(jsonPath("$.name").value(organization.getName()))
                .andExpect(jsonPath("$.location").value(organization.getLocation()))
                .andExpect(jsonPath("$.phone").value(organization.getPhone()))
                .andExpect(jsonPath("$.email").value(organization.getEmail()))
                .andExpect(jsonPath("$.visible").value(organization.getVisible()))
                .andExpect(jsonPath("$.events[0]").value(1L))
                .andExpect(jsonPath("$.customerId").value(organization.getCustomerId()));
    }

    @Test
    public void testGetByIdNull() throws Exception {
        mockMvc.perform(get("/organizations/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not found"));
    }


    @Test
    public void testCreate() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        when(userService.existsCustomer("1")).thenReturn(true);
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));

        organizationRepository.findById(1L).orElseThrow(AssertionError::new);
    }

    @Test
    public void testCreateWithOrganizationNameExistException() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        when(userService.existsCustomer("1")).thenReturn(true);
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Name exist"));
    }

    @Test
    public void testCreateWithOrganizationNameNull() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                null,
                "loc",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithOrganizationNameEmpty() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "",
                "loc",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithLocationNameNull() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                null,
                "1111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithOLocationEmpty() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "",
                "1111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithPhoneMore10() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "11111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithPhoneLess10() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "111111111",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithPhoneEmpty() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "",
                "email@gmail.com",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithEmailNotCorrect() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithEmailEmpty() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "",
                "1"
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithCustomerIdNull() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email@gmail.com",
                null
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithCustomerIdEmpty() throws Exception {
        OrganizationCreateDto dto = new OrganizationCreateDto(
                "name",
                "loc",
                "1111111111",
                "email@gmail.com",
                ""
        );
        mockMvc.perform(put("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:scripts/createEvent.sql")
    public void testUpdate() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(dto.getId()));
    }

    @Test
    @Sql("classpath:scripts/createEvent.sql")
    public void testUpdateWithOrganizationNameExistException() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name45461",
                "loc",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Name exist"));
    }

    @Test
    public void testUpdateWithIdNull() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                null,
                "name45461",
                "loc",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithNameNull() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                null,
                "loc",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithNameEmpty() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "",
                "loc",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithLocationNull() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                null,
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithLocationEmpty() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "",
                "1111111111",
                "email@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    public void testUpdateWithNotCorrectEmail() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "1111111111",
                "emai="
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWitEmailNull() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "1111111111",
                null
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWitEmailEmpty() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "",
                "1111111111",
                ""
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithPhoneNull() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                null,
                "sadf@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithPhoneEmpty() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "",
                "sadf@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithPhoneMore10() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "11111111111",
                "sadf@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithPhoneLess10() throws Exception {
        OrganizationUpdateDto dto = new OrganizationUpdateDto(
                1L,
                "name",
                "loc",
                "111111111",
                "sadf@gmail.com"
        );

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:scripts/createEvent.sql")
    public void testDelete() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/organizations/{id}", id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/organizations/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visible").value(false));
    }

    @Test
    public void testDeleteOrganizationNotExist() throws Exception {
        mockMvc.perform(delete("/organizations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Organization not found"));
    }

    @Test
    public void testDeleteIdNull() throws Exception {
        Long id = null;

        mockMvc.perform(delete("/organizations/{id}", id))
                .andExpect(status().isMethodNotAllowed());
    }
}