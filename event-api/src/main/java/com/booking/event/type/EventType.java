package com.booking.event.type;

public enum EventType {

    ORIGINAL_CONCERT(Values.ORIGINAL_CONCERT),
    COVER_CONCERT(Values.COVER_CONCERT),
    CINEMA(Values.CINEMA),
    THEATRE(Values.THEATRE);

    public final String value;

    EventType(String value) {
        this.value = value;
    }

    public static class Values {
        public static final String ORIGINAL_CONCERT = "ORIGINAL_CONCERT";
        public static final String COVER_CONCERT = "COVER_CONCERT";
        public static final String CINEMA = "CINEMA";
        public static final String THEATRE = "THEATRE";
    }
}
