package com.booking.event.persistence.entity;

import com.booking.event.persistence.entity.event.AbstractEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean visible = false;

    @OneToMany(mappedBy = "organization")
    private Set<AbstractEvent> abstractEvents;
}
