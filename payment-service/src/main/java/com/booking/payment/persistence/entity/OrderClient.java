package com.booking.payment.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "payment_client_id")
    private Payment paymentClient;

    @ManyToOne
    @JoinColumn(name = "payment_customer_id")
    private Payment paymentCustomer;

    @Column(nullable = false)
    private int amount;
}