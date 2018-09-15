package com.booking.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;

    private Long paymentId;

    private String name;

    private String surname;

    private UserType type;

    private String email;

    private String phone;
}
