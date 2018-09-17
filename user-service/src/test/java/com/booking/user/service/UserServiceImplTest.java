package com.booking.user.service;

import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import com.booking.user.persistence.repository.UserRepository;
import com.booking.user.service.feign.PaymentService;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import com.booking.user.transpor.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    @Spy
    private UserServiceImpl userService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PaymentService paymentService;

    @Test
    public void testGetAll() {
        UserOutcomeDto userOutcomeDto = new UserOutcomeDto();
        User user = new User();
        List<UserOutcomeDto> userOutcomeDtos = Lists.newArrayList(userOutcomeDto);
        when(mongoTemplate.find(
                any(Query.class),
                any()
        )).thenReturn(Lists.newArrayList(user));
        when(userMapper.toDto(Lists.newArrayList(user))).thenReturn(userOutcomeDtos);

        List<UserOutcomeDto> result = userService.getAll(
                new UserFindDto(),
                PageRequest.of(0, 10)
        );

        verify(mongoTemplate, times(1)).find(any(Query.class), any());
        verify(userMapper, times(1)).toDto(Lists.newArrayList(user));
        assertEquals(userOutcomeDtos, result);
    }

    @Test
    public void testCreate() {
        UserCreateDto userCreateDto = new UserCreateDto("gmail", 1L);
        User user = new User();
        user.setId("1");
        when(userRepository.existsByEmail(userCreateDto.getEmail())).thenReturn(false);
        when(paymentService.existTokenId(userCreateDto.getPaymentId())).thenReturn(true);
        when(userMapper.toEntity(userCreateDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        String result = userService.create(userCreateDto);

        verify(userRepository, times(1)).existsByEmail(userCreateDto.getEmail());
        verify(paymentService, times(1)).existTokenId(userCreateDto.getPaymentId());
        verify(userMapper, times(1)).toEntity(userCreateDto);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getId(), result);
    }

    @Test
    public void testExistsCustomer() {
        String id = "1";
        when(userRepository.existsByIdAndType(id, UserType.CUSTOMER)).thenReturn(true);

        boolean result = userService.existsCustomer(id);

        verify(userRepository, times(1)).existsByIdAndType(id, UserType.CUSTOMER);
        assertTrue(result);
    }

    @Test
    public void testExistsCustomerByPaymentId() {
        Long paymentId = 1L;
        when(userRepository.existsByPaymentIdAndType(paymentId, UserType.CUSTOMER)).thenReturn(true);

        boolean result = userService.existsCustomerByPaymentId(paymentId);

        verify(userRepository, times(1)).existsByPaymentIdAndType(paymentId, UserType.CUSTOMER);
        assertTrue(result);
    }

    @Test
    public void testExistsClientByPaymentId() {
        Long paymentId = 1L;
        when(userRepository.existsByPaymentIdAndType(paymentId, UserType.CLIENT)).thenReturn(true);

        boolean result = userService.existsClientByPaymentId(paymentId);

        verify(userRepository, times(1)).existsByPaymentIdAndType(paymentId, UserType.CLIENT);
        assertTrue(result);
    }

    @Test
    public void testGetUserByPaymentId() {
        Long paymentClientId = 1L;
        List<UserOutcomeDto> userOutcomeDtos = Lists.newArrayList(new UserOutcomeDto());
        doReturn(userOutcomeDtos).when(userService).getAll(
                any(UserFindDto.class),
                any()
        );

        UserOutcomeDto result = userService.getUserByPaymentId(paymentClientId);

        verify(userService, times(1)).getAll(
                any(UserFindDto.class),
                any());
        assertEquals(userOutcomeDtos.get(0), result);
    }
}