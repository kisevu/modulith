package com.ameda.kev.smartparkingmodulith.allocation.domain;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: kev.Ameda
 */
public enum Blocks {
    BLOCKA("BLOCK A",4,ParkingSlotEnum.DEFAULT_STATUS),
    BLOCKB("BLOCK B",4, ParkingSlotEnum.DEFAULT_STATUS),
    BLOCKC("BLOCK C",4,ParkingSlotEnum.DEFAULT_STATUS);

    private final String displayName;
    private final Integer maxSlots;
    private final ParkingSlotEnum status;

    //Track occupied count per block
    private static final Map<Blocks, AtomicInteger> occupiedCounts
            = new ConcurrentHashMap<>();

    Blocks(String displayName, Integer maxSlots, ParkingSlotEnum status) {
        this.displayName = displayName;
        this.maxSlots = maxSlots;
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getMaxSlots() {
        return maxSlots;
    }

    public ParkingSlotEnum getStatus() {
        return status;
    }


}
