package com.ameda.kev.smartparkingmodulith.allocation.domain;

/**
 * Author: kev.Ameda
 */
public enum ParkingSlotEnum {
    AVAILABLE, OCCUPIED, RESERVED;

    public static final ParkingSlotEnum  DEFAULT_STATUS = AVAILABLE;
}
