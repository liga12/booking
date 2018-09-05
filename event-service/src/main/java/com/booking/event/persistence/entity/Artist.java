package com.booking.event.persistence.entity;

import com.booking.event.persistence.entity.event.AbstractEvent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "artists")
    private Set<AbstractEvent> abstractEvents;


}
