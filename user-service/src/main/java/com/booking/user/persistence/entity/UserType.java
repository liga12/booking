package com.booking.user.persistence.entity;

public enum UserType {

    CLIENT(Values.CLIENT),
    CUSTOMER(Values.CUSTOMER);

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public static class Values {
        static final String CLIENT = "CLIENT";
        static final String CUSTOMER = "CUSTOMER";
    }
}
