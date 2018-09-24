package com.booking.user.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
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

    public User(Long paymentId, String name, String surname, UserType type, String email, String phone) {
        this.paymentId = paymentId;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.email = email;
        this.phone = phone;
    }
}
