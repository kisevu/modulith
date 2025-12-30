package com.ameda.kev.smartparkingmodulith.allocation.domain;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: kev.Ameda
 */
public enum Blocks {
    BLOCKA("BLOCK A"),
    BLOCKB("BLOCK B"),
    BLOCKC("BLOCK C");


    private final String displayName;

    Blocks(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
