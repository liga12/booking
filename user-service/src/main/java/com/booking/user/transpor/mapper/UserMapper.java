package com.booking.user.transpor.mapper;

import com.booking.user.persistence.entity.User;
import com.booking.user.transpor.dto.UserCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateDto dto);
}
