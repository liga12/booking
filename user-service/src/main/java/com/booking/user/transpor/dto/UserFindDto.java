package com.booking.user.transpor.dto;

import com.booking.user.persistence.entity.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindDto {

    private String id;

    private Long paymentId;

    private String name;

    private String surname;

    private UserType type;

    private String email;

    private String phone;
}
