package com.booking.event.persistence.entity.event;

import com.booking.event.persistence.entity.Organization;
import com.booking.event.persistence.entity.place.Place;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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

    private String artists;

    @ManyToMany
    @JoinTable(name = "events_places",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Place> places;

    public AbstractEvent(EventType type) {
        this.type = type;
    }
}
