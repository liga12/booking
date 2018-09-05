package com.booking.event.persistence.entity.event;

public enum EventType {

    ORIGINAL_CONCERT(Values.ORIGINAL_CONCERT),
    COVER_CONCERT(Values.COVER_CONCERT),
    CINEMA(Values.CINEMA),
    THEATRE(Values.THEATRE);

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public static class Values {
        static final String ORIGINAL_CONCERT = "ORIGINAL_CONCERT";
        static final String COVER_CONCERT = "COVER_CONCERT";
        static final String CINEMA = "CINEMA";
        static final String THEATRE = "THEATRE";
    }
}
