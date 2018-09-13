package com.booking.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
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

    @TextIndexed
    private String name;

    @TextIndexed
    private String surname;

    private UserType type;

    @TextIndexed
    private String email;

    @TextIndexed
    private String phone;
}
