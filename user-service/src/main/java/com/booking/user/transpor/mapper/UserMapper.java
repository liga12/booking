package com.booking.user.transpor.mapper;

import com.booking.user.persistence.entity.User;
import com.booking.user.transpor.dto.UserCreateDto;
import com.booking.user.transpor.dto.UserOutcomeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateDto dto);

    UserOutcomeDto toDto(User user);

    List<UserOutcomeDto> toDto(List<User> user);
}
