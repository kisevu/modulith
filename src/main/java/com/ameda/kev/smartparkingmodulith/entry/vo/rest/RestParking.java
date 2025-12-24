package com.ameda.kev.smartparkingmodulith.entry.vo.rest;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.vo.ParkingActiveStatus;

import org.jilt.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Builder
public record RestParking(String name,
                          Instant entryTime,
                          Instant exitTime,
                          ParkingActiveStatus status,
                          UUID vehiclePublicId
                          ) {

    public static RestParking from(Parking parking){
         return RestParkingBuilder.restParking()
                 .name(parking.getName().name())
                 .entryTime(parking.getEntryTime())
                 .exitTime(parking.getExitTime())
                 .status(new ParkingActiveStatus(parking.getStatus()))
                 .vehiclePublicId(parking.getPublicId().value())
                 .build();

    }
}
