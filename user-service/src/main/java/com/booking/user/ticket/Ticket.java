package com.booking.user.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    private String name;

    private String surname;

    private String event;

    private int placeNumber;

    private int placeRow;

    private String date;

    private double cost;

    private String organizationPhone;
}
