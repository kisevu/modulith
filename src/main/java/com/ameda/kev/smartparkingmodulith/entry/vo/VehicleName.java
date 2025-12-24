package com.ameda.kev.smartparkingmodulith.entry.vo;

import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;

/**
 * Author: kev.Ameda
 */
public record VehicleName(String name) {
    public VehicleName {
        Assert.field("name",name).maxLength(255);
    }

}
