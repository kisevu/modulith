package com.ameda.kev.smartparkingmodulith.entry.vo;

import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;

/**
 * Author: kev.Ameda
 */
public record ParkingActiveStatus(ParkingStatus status) {
    public ParkingActiveStatus {
        Assert.notNull("status",status);
    }
}
