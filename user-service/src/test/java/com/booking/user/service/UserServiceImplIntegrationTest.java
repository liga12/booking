package com.booking.user.service;

import com.booking.user.exception.EmailExistException;
import com.booking.user.exception.TokenNotExistException;
import com.booking.user.exception.UserNotFoundException;
import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.feign.PaymentService;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.booking.user.transpor.mapper.UserMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.booking.user.asserts.Asserts.assertEqualUserAndDto;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @MockBean
    private PaymentService paymentService;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAll() {
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(firsUser, secondUser));
        UserFindDto searchDto = new UserFindDto();
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithPaymentId() {
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(firsUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setPaymentId(1L);
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithName() {
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(secondUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setName("2");
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithSurname() {
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(secondUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setSurname("2");
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithType() {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(secondUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setType(UserType.CLIENT);
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithEmail() {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
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
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(secondUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setEmail("2");
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testGetAllWithPhone() {
        User firsUser = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        User secondUser = new User(
                2L,
                "name2",
                "surname2",
                UserType.CLIENT,
                "email2",
                "111112"
        );
        userRepository.save(firsUser);
        userRepository.save(secondUser);
        List<UserOutcomeDto> users = userMapper.toDto(Lists.newArrayList(secondUser));
        UserFindDto searchDto = new UserFindDto();
        searchDto.setPhone("2");
        PageRequest pageable = PageRequest.of(0, 10);

        List<UserOutcomeDto> result = userService.getAll(searchDto, pageable);

        assertEqualUserAndDto(users, result);
    }

    @Test
    public void testCreate() {
        UserCreateDto dto = new UserCreateDto(
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111",
                1L
        );
        when(paymentService.existTokenId(dto.getPaymentId())).thenReturn(true);

        String result = userService.create(dto);

        UserFindDto findDto = new UserFindDto();
        findDto.setName("name");
        PageRequest pageable = PageRequest.of(0, 10);
        assertEquals(userService.getAll(findDto, pageable).get(0).getId(), result);
    }

    @Test(expected = EmailExistException.class)
    public void testCreateWithEmailExistException() {
        UserCreateDto dto = new UserCreateDto(
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111",
                1L
        );
        userRepository.save(userMapper.toEntity(dto));

        userService.create(dto);
    }

    @Test(expected = EmailExistException.class)
    public void testCreateWithNullEmail() {
        UserCreateDto dto = new UserCreateDto(
                "name",
                "surname",
                UserType.CUSTOMER,
                null,
                "11",
                1L
        );

        userService.create(dto);
    }

    @Test(expected = TokenNotExistException.class)
    public void testCreateWithTokenNotExistException() {
        UserCreateDto dto = new UserCreateDto(
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "11",
                1L
        );

        userService.create(dto);
    }

    @Test(expected = TokenNotExistException.class)
    public void testCreateWithNullToken() {
        UserCreateDto dto = new UserCreateDto(
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "11",
                null
        );

        userService.create(dto);
    }

    @Test
    public void testExistsCustomer() {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        userRepository.save(user);

        boolean result = userService.existsCustomer(user.getId());

        assertTrue(result);
    }

    @Test
    public void testExistsCustomerFalse() {
        boolean result = userService.existsCustomer("1");

        assertFalse(result);
    }

    @Test
    public void testExistsCustomerIdNull() {
        boolean result = userService.existsCustomer(null);

        assertFalse(result);
    }

    @Test
    public void testExistsCustomerByPaymentId() {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CUSTOMER,
                "email",
                "1111"
        );
        userRepository.save(user);

        boolean result = userService.existsCustomerByPaymentId(1L);

        assertTrue(result);
    }

    @Test
    public void testExistsCustomerByPaymentIdFalse() {
        boolean result = userService.existsCustomerByPaymentId(1L);

        assertFalse(result);
    }

    @Test
    public void testExistsCustomerByPaymentIdNull() {
        boolean result = userService.existsCustomerByPaymentId(null);

        assertFalse(result);
    }

    @Test
    public void testExistsClientByPaymentId() {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        boolean result = userService.existsClientByPaymentId(1L);

        assertTrue(result);
    }

    @Test
    public void testExistsClientByPaymentIdFalse() {
        boolean result = userService.existsClientByPaymentId(1L);

        assertFalse(result);
    }

    @Test
    public void testExistsClientByPaymentIdNull() {
        boolean result = userService.existsClientByPaymentId(null);

        assertFalse(result);
    }

    @Test
    public void testGetUserByPaymentId() {
        User user = new User(
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "1111"
        );
        userRepository.save(user);

        UserOutcomeDto result = userService.getUserByPaymentId(1L);

        assertEqualUserAndDto(userMapper.toDto(user), result);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByPaymentIdWithUserNotFoundException() {
       userService.getUserByPaymentId(1L);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByPaymentIdNull() {
      userService.getUserByPaymentId(null);
    }
}