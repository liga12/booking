package com.booking.user.controller;

import com.booking.user.persistence.entity.UserType;
import com.booking.user.service.UserService;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.booking.user.util.Converter.mapToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        UserOutcomeDto dto = new UserOutcomeDto(
                "1",
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111");
        when(userService.getAll(
                any(UserFindDto.class),
                any(Pageable.class)
        )).thenReturn(Lists.newArrayList(dto));

        mockMvc.perform(get("/users?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dto.getId()))
                .andExpect(jsonPath("$[0].paymentId").value(dto.getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(dto.getName()))
                .andExpect(jsonPath("$[0].surname").value(dto.getSurname()))
                .andExpect(jsonPath("$[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(dto.getPhone()));

        verify(userService, times(1)).getAll(any(UserFindDto.class), any(Pageable.class));
    }

    @Test
    public void testRegistration() throws Exception {
        String id = "1";
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );
        when(userService.create(createDto)).thenReturn(id);

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));

        verify(userService, times(1)).create(createDto);
    }

    @Test
    public void testRegistrationWithNameNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                null,
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithNameEmpty() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithSurnameNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                null,
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithSurnameEmpty() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                null,
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithTypeNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                null,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithEmailNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                null,
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithEmailEmpty() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWithNotCorrectEmail() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "e",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWitPhoneNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                null,
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWitPhoneEmpty() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistrationWitPaymentIdNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "",
                null
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }

}