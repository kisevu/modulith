package com.ameda.kev.smartparkingmodulith.shared.domain;

import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;

/**
 * Author: kev.Ameda
 */
@Builder
public record VehicleEventObj(String ownerName,
                              Instant entryTime,
                              Instant exitTime,
                              String vehicleRegNo,
                              String ownerIdNo) {

    public VehicleEventObj {
        Assert.notNull("ownerName",ownerName);
        Assert.notNull("entryTime",entryTime);
        Assert.notNull("exitTime",exitTime);
        Assert.notNull("vehicleRegNo",vehicleRegNo);
        Assert.notNull("ownerIdNo",ownerIdNo);
    }
}
