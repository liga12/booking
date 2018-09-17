package com.booking.event.persistence.entity.event;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.place.Place;
import com.booking.event.type.EventType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "typeDiscrimination")
public abstract class AbstractEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    private EventType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(nullable = false)
    private String artists;

    @Column(nullable = false)
    private Boolean visible = true;

    @OneToMany(mappedBy = "event")
    private Set<Place> places;

    public AbstractEvent(EventType type) {
        this.type = type;
    }
}
