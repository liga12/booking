package com.booking.user.service;

import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserFindDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserOutcomeDto> getAll(UserFindDto dto, Pageable pageable);

    String create(UserCreateDto dto);

    boolean existsCustomer(String id);

    boolean existsCustomerByPaymentId(Long paymentId);

    boolean existsClientByPaymentId(Long paymentId);

    UserOutcomeDto getUserByPaymentId(Long paymentClientId);
}
