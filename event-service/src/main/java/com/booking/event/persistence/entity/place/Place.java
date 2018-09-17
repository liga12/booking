package com.booking.event.persistence.entity.place;

import com.booking.event.persistence.entity.event.AbstractEvent;
import com.booking.event.type.PlaceStatusType;
import com.booking.event.type.SectionType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer row;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceStatusType status = PlaceStatusType.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private AbstractEvent event;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;
}
