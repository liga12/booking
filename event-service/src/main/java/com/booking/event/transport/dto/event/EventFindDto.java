package com.booking.event.transport.dto.event;

import com.booking.event.type.EventType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EventFindDto {

    private Long id;

    private String name;

    private EventType type;

    private String description;

    private Long starDate;

    private Long endDate;

    private String location;

    private String photoUrl;

    private Long organization;

    private String artists;

    private Set<Long> places;

    private Boolean visible;
}
