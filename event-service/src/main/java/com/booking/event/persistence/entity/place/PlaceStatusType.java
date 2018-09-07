package com.booking.event.persistence.entity.place;

public enum PlaceStatusType {

    ACTIVE(Values.ACTIVE),
    ORDER(Values.ORDER),
    BYU(Values.BYU),
    NOT_ACTIVE(Values.NOT_ACTIVE);

    private final String value;

    PlaceStatusType(String value) {
        this.value = value;
    }

    public static class Values {
        static final String ACTIVE = "ACTIVE";
        static final String ORDER = "ORDER";
        static final String BYU = "BYU";
        static final String NOT_ACTIVE = "NOT_ACTIVE";
    }
}
