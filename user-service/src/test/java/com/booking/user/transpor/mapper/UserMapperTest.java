package com.booking.user.transpor.mapper;

import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;


    @Test
    public void testToEntity() {
        UserCreateDto userCreateDto = new UserCreateDto(
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "phone",
                1L
        );

        User result = userMapper.toEntity(userCreateDto);

        assertEquals(userCreateDto.getName(), result.getName());
        assertEquals(userCreateDto.getSurname(), result.getSurname());
        assertEquals(userCreateDto.getType(), result.getType());
        assertEquals(userCreateDto.getEmail(), result.getEmail());
        assertEquals(userCreateDto.getPhone(), result.getPhone());
        assertEquals(userCreateDto.getPaymentId(), result.getPaymentId());
    }

    @Test
    public void testToUserOutcomeDto() {
        User user = new User(
                "1",
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "phone");

        UserOutcomeDto result = userMapper.toDto(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPaymentId(), result.getPaymentId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurname(), result.getSurname());
        assertEquals(user.getType(), result.getType());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPhone(), result.getPhone());
    }

    @Test
    public void testToListUserOutcomeDto() {
        User user = new User(
                "1",
                1L,
                "name",
                "surname",
                UserType.CLIENT,
                "email",
                "phone");
        List<User> users = Lists.newArrayList(user);

        List<UserOutcomeDto> result = userMapper.toDto(users);

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getId(), result.get(0).getId());
        assertEquals(users.get(0).getPaymentId(), result.get(0).getPaymentId());
        assertEquals(users.get(0).getName(), result.get(0).getName());
        assertEquals(users.get(0).getSurname(), result.get(0).getSurname());
        assertEquals(users.get(0).getType(), result.get(0).getType());
        assertEquals(users.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(users.get(0).getPhone(), result.get(0).getPhone());
    }
}