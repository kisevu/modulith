package com.ameda.kev.smartparkingmodulith.entry.service;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.repository.ParkingRepository;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import com.ameda.kev.smartparkingmodulith.shared.events.VehicleParkingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Parking createParking(Parking parking){
        parking.initFieldOnCreation();
        parking.setLastSeen(Instant.now());
        Parking savedParking = parkingRepository.save(parking);
        eventPublisher.publishEvent(new VehicleParkingEvent(UUID.randomUUID(),
                new VehicleEventObj(savedParking.getOwnerName(),
                        savedParking.getEntryTime(),
                        savedParking.getExitTime(),
                        savedParking.getVehicleRegNo(),
                        savedParking.getOwnerIdNo())));
        return savedParking;
    }

    public Optional<Parking> getParkingByPublicId(VehiclePublicId vehiclePublicId){
        return parkingRepository.findByPublicId(vehiclePublicId.value());
    }

    public Page<Parking> getAllParking(Pageable pageable){
        return parkingRepository.findAll(pageable);
    }

    /*
    *  TODOs:
    *  - Request for a slot / ticket
    *  - Persist the vehicle
    *  - Allocate a parking slot
    *  - Send notification to user
    *  - Initiate payment
    *  - Send notification to acknowledge payment
    *  - free up the previous located space and add to the request queue
    * */


}
