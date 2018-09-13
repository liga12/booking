package com.booking.user.service;

import com.booking.user.transpor.dto.UserCreateDto;

public interface UserService {

    String create(UserCreateDto dto);

    boolean existsCustomer(String id);
}
