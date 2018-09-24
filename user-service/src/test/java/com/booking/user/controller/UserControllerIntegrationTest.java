package com.booking.user.controller;

import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.feign.PaymentService;
import com.booking.user.transpor.dto.UserCreateDto;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.booking.user.util.Converter.mapToJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private PaymentService paymentService;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        List<User> users = userRepository.findAll();

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentId").value(users.get(1).getPaymentId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].type").value(users.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phone").value(users.get(1).getPhone()));
    }

    @Test
    public void testGetAllWithId() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        List<User> users = Lists.newArrayList(
                userRepository.findById(firsUser.getId())
                        .orElseThrow(AssertionError::new)
        );

        mockMvc.perform(get("/users?id={valId}", firsUser.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()));
    }

    @Test
    public void testGetAllByPaymentId() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        List<User> users = Lists.newArrayList(
                userRepository.findById(firsUser.getId())
                        .orElseThrow(AssertionError::new)
        );

        mockMvc.perform(get("/users?paymentId={id}", firsUser.getPaymentId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()));
    }

    @Test
    public void testGetAllByName() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );
        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = (List<User>) userRepository.findAllById(
                Lists.newArrayList(
                        firsUser.getId(),
                        secondUser.getId()
                )
        );

        mockMvc.perform(get("/users?name=name")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentId").value(users.get(1).getPaymentId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].type").value(users.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phone").value(users.get(1).getPhone()));
    }

    @Test
    public void testGetAllBySurname() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );
        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = (List<User>) userRepository.findAllById(
                Lists.newArrayList(
                        firsUser.getId(),
                        secondUser.getId()
                )
        );

        mockMvc.perform(get("/users?surname=surname")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentId").value(users.get(1).getPaymentId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].type").value(users.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phone").value(users.get(1).getPhone()));
    }

    @Test
    public void testGetAllByType() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CUSTOMER,
                "email2",
                "11111"
        );
        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CUSTOMER,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = (List<User>) userRepository.findAllById(
                Lists.newArrayList(
                        firsUser.getId(),
                        secondUser.getId()
                )
        );

        mockMvc.perform(get("/users?type=CLIENT")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()));
    }

    @Test
    public void testGetAllByEmail() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "11111"
        );

        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = (List<User>) userRepository.findAllById(
                Lists.newArrayList(
                        firsUser.getId(),
                        secondUser.getId()
                )
        );

        mockMvc.perform(get("/users?email=email")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentId").value(users.get(1).getPaymentId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].type").value(users.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phone").value(users.get(1).getPhone()));
    }

    @Test
    public void testGetAllByPhone() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "11112"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "111112"
        );

        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = (List<User>) userRepository.findAllById(
                Lists.newArrayList(
                        firsUser.getId(),
                        secondUser.getId()
                )
        );

        mockMvc.perform(get("/users?phone=12")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()))
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
                .andExpect(jsonPath("$[1].paymentId").value(users.get(1).getPaymentId()))
                .andExpect(jsonPath("$[1].name").value(users.get(1).getName()))
                .andExpect(jsonPath("$[1].type").value(users.get(1).getType().toString()))
                .andExpect(jsonPath("$[1].surname").value(users.get(1).getSurname()))
                .andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$[1].phone").value(users.get(1).getPhone()));
    }

    @Test
    public void testGetAllByAll() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "11112"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "111112"
        );

        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = Lists.newArrayList(
                userRepository.findById(firsUser.getId())
                        .orElseThrow(AssertionError::new)
        );

        mockMvc.perform(get("/users?phone=12&name=name&surname=surname&id={valId}", firsUser.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()));
    }

    @Test
    public void testGetAllByAllAndPageable() throws Exception {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "11112"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "111112"
        );

        User thirdUser = new User(
                3L,
                "ame2",
                "urname2",
                UserType.CLIENT,
                "mail2",
                "1111"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
        List<User> users = Lists.newArrayList(
                userRepository.findById(secondUser.getId())
                        .orElseThrow(AssertionError::new)
        );

        mockMvc.perform(get("/users?name=name&surname=surname&page=0&size=1&sort=name,DESC")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
                .andExpect(jsonPath("$[0].paymentId").value(users.get(0).getPaymentId()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].type").value(users.get(0).getType().toString()))
                .andExpect(jsonPath("$[0].surname").value(users.get(0).getSurname()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].phone").value(users.get(0).getPhone()));
    }

    @Test
    public void testRegistration() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );
        when(paymentService.existTokenId(createDto.getPaymentId())).thenReturn(true);

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(userRepository.findAll().get(0).getId()));
    }

    @Test
    public void testRegistrationWithTokenNotExistException() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );
        when(paymentService.existTokenId(createDto.getPaymentId())).thenReturn(false);

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Token not exist"));
    }

    @Test
    public void testRegistrationWithEmailExistException() throws Exception {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "11112"
        );
        userRepository.save(user);
        UserCreateDto createDto = new UserCreateDto(
                "name",
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Email exist"));
    }

    @Test
    public void testRegistrationWithEmptyName() throws Exception {
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
    public void testRegistrationWithEmptySurname() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "",
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
    public void testRegistrationWithNotCorrectType() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "1111",
                1L
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto).replace("CLIENT", "CLIENT1")))
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
    public void testRegistrationWithEmptyEmail() throws Exception {
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
    public void testRegistrationWithEmailNotCorrect() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "dsfd",
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
    public void testRegistrationWithPhone() throws Exception {
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
    public void testRegistrationWithPhoneNull() throws Exception {
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
    public void testRegistrationWithPaymentIdNull() throws Exception {
        UserCreateDto createDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email@gmail.com",
                "111",
                null
        );

        mockMvc.perform(put("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(createDto)))
                .andExpect(status().isBadRequest());
    }


}