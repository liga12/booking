package com.booking.payment.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToMany(mappedBy = "paymentClient")
    private Set<OrderClient> client;

    @OneToMany(mappedBy = "paymentClient")
    private Set<OrderClient> customer;

    public Payment(Long id) {
        this.id = id;
    }

    public Payment(String token) {
        this.token = token;
    }
}
