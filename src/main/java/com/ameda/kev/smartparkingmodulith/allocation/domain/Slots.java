package com.ameda.kev.smartparkingmodulith.allocation.domain;

/**
 * Author: kev.Ameda
 */
public enum Slots {
    SLOTA("SLOT A"),
    SLOTB("SLOT B"),
    SLOTC("SLOT C"),
    SLOTD("SLOT D");

    private final String displayname;

    Slots(String displayname) {
        this.displayname = displayname;
    }

    public String getDisplayname() {
        return displayname;
    }
}
