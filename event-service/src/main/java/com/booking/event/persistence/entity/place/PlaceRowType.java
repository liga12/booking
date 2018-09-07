package com.booking.event.persistence.entity.place;

public enum PlaceRowType {

    VIP(Values.VIP),
    FREE(Values.FREE),
    FIRST_ROW(Values.FIRST_ROW),
    MIDDLE_ROW(Values.MIDDLE_ROW),
    LAST_ROW(Values.LAST_ROW);

    private final String value;

    PlaceRowType(String value) {
        this.value = value;
    }

    public static class Values {
        static final String VIP = "VIP";
        static final String FREE = "FREE";
        static final String FIRST_ROW = "FIRST_ROW";
        static final String MIDDLE_ROW = "MIDDLE_ROW";
        static final String LAST_ROW = "LAST_ROW";
    }
}
