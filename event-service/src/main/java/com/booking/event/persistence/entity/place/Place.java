package com.booking.event.persistence.entity.place;

import com.booking.event.persistence.entity.event.AbstractEvent;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceStatusType status;

    @ManyToMany(mappedBy = "places")
    private Set<AbstractEvent> abstractEvents;

    @Enumerated(EnumType.STRING)
    private PlaceRowType rowType;
}
