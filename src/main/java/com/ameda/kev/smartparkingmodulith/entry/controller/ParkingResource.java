package com.ameda.kev.smartparkingmodulith.entry.controller;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.exceptions.ParkingEntityNotFoundException;
import com.ameda.kev.smartparkingmodulith.entry.service.ParkingApplicationService;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import com.ameda.kev.smartparkingmodulith.entry.vo.rest.RestParking;
import com.ameda.kev.smartparkingmodulith.entry.vo.rest.RestParkingRequest;
import com.ameda.kev.smartparkingmodulith.entry.vo.rest.RestParkingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/api/parking")
public class ParkingResource {

    private final ParkingApplicationService parkingApplicationService;
    private final Logger log = LoggerFactory.getLogger(ParkingResource.class);

    public ParkingResource(ParkingApplicationService parkingApplicationService) {
        this.parkingApplicationService = parkingApplicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestParkingResponse> createParking(@RequestBody RestParkingRequest restParkingRequest){
        Parking parking = RestParkingRequest.to(restParkingRequest);
        Parking parkingSpot = parkingApplicationService.createParkingSpot(parking);
        if( parkingSpot == null ){
            return ResponseEntity.badRequest().build();
        }
        log.info("Assign a parking slot : {}",Thread.currentThread().getName());
        return ResponseEntity.ok(RestParkingResponse.from(parkingSpot));
    }

    @GetMapping("/single")
    public ResponseEntity<RestParking> getParking(@RequestParam("publicId") UUID parkingPublicId){
        Optional<Parking> parking = parkingApplicationService.getParking(new VehiclePublicId(parkingPublicId));
        try{
            RestParking restParking = RestParking.from(parking.get());
            return ResponseEntity.ok(restParking);
        }catch (ParkingEntityNotFoundException ex){
            log.error(" An Error occurred: {}", ex.getCause());
            return ResponseEntity.badRequest().build();
        }
    }
}
