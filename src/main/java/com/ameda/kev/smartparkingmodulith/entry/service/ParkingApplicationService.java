package com.ameda.kev.smartparkingmodulith.entry.service;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.repository.ParkingRepository;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Author: kev.Ameda
 */

@Service
public class ParkingApplicationService {
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

    public ParkingApplicationService(ParkingRepository parkingRepository, ParkingService parkingService) {
        this.parkingService =  parkingService;
        this.parkingRepository = parkingRepository;
    }

    @Transactional
    public Parking createParkingSpot(Parking newParking){
        return parkingService.createParking(newParking);
    }

    @Transactional(readOnly = true)
    public Optional<Parking>  getParking(VehiclePublicId vehiclePublicId){
        return parkingService.getParkingByPublicId(vehiclePublicId);
    }

    @Transactional(readOnly = true)
    public Page<Parking> allParking(Pageable pageable){
        return parkingService.getAllParking(pageable);
    }


}
