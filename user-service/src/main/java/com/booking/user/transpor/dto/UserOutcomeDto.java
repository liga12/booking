package com.booking.user.transpor.dto;

import com.booking.user.persistence.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutcomeDto {

    private String id;

    private Long paymentId;

    private String name;

    private String surname;

    private UserType type;

    private String email;

    private String phone;
}
