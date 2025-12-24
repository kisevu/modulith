package com.ameda.kev.smartparkingmodulith.entry.vo.rest;
import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.domain.ParkingBuilder;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehicleName;
import com.ameda.kev.smartparkingmodulith.entry.vo.domain.*;
import org.jilt.Builder;

import java.time.Instant;

/**
 * Author: kev.Ameda
 */
@Builder
public record RestParkingRequest(String vehicleName,
                                 String vehicleRegNo,
                                 String ownerName,
                                 String ownerIdNo
                                 ) {

    public RestParkingRequest {
        Assert.notNull("vehicleName",vehicleName);
        Assert.notNull("vehicleRegNo",vehicleRegNo);
        Assert.notNull("ownerName",ownerName);
        Assert.notNull("ownerIdNo",ownerIdNo);
    }

    public static RestParkingRequest from(Parking parking){
     return  RestParkingRequestBuilder.restParkingRequest()
                .vehicleName(parking.getOwnerName())
                .vehicleRegNo(parking.getVehicleRegNo())
                .ownerName(parking.getOwnerName())
                .ownerIdNo(parking.getOwnerIdNo())
                .build();

    }

    public static Parking to(RestParkingRequest restParkingRequest){
       return ParkingBuilder.parking()
               .name(new VehicleName(restParkingRequest.vehicleName))
               .vehicleRegNo(restParkingRequest.vehicleRegNo())
               .ownerName(restParkingRequest.ownerName())
               .ownerIdNo(restParkingRequest.ownerIdNo())
               .lastSeen(Instant.now())
               .build();
    }
}
