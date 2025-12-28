package com.ameda.kev.smartparkingmodulith.entry.vo.rest;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.vo.ParkingActiveStatus;
import com.ameda.kev.smartparkingmodulith.entry.vo.ParkingStatus;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehicleName;
import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;

/**
 * Author: kev.Ameda
 */
@Builder
public record RestParkingResponse(String vehicleName,
                                  ParkingStatus activateStatus) {

    public RestParkingResponse {
        Assert.notNull("vehicleName",vehicleName);
        Assert.notNull("activateStatus",activateStatus);
    }

    public static RestParkingResponse from(Parking parking){
      return RestParkingResponseBuilder.restParkingResponse()
              .vehicleName(parking.getName().name())
              .activateStatus(parking.getStatus())
              .build();
    }
}
