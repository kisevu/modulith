package com.ameda.kev.smartparkingmodulith.entry.vo;


import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;

import java.util.UUID;

/**
 * Author: kev.Ameda
 */
public record VehiclePublicId(UUID value) {
    public VehiclePublicId {
        Assert.notNull("value",value);
    }
}
