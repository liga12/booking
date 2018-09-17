package com.booking.payment.persistence.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
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
}
