package com.booking.event.persistence.entity;

import com.booking.event.persistence.entity.event.AbstractEvent;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "artists")
    private Set<AbstractEvent> abstractEvents;


}
