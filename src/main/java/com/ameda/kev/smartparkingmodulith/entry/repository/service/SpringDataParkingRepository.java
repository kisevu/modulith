package com.ameda.kev.smartparkingmodulith.entry.repository.service;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.entity.ParkingEntity;
import com.ameda.kev.smartparkingmodulith.entry.repository.JpaParkingRepository;
import com.ameda.kev.smartparkingmodulith.entry.repository.ParkingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Repository
public class SpringDataParkingRepository implements ParkingRepository {
    private final JpaParkingRepository jpaParkingRepository;

    public SpringDataParkingRepository(JpaParkingRepository jpaParkingRepository) {
        this.jpaParkingRepository = jpaParkingRepository;
    }

    @Override
    public Parking save(Parking parking) {
        ParkingEntity parkingEntity = ParkingEntity.fromDomain(parking);
        ParkingEntity savedParkingEntity = jpaParkingRepository.save(parkingEntity);
        return ParkingEntity.toDomain(savedParkingEntity);
    }

    @Override
    public Optional<Parking> findByPublicId(UUID publicId) {
        return jpaParkingRepository.findByPublicId(publicId)
                .map(ParkingEntity::toDomain);
    }

    @Override
    public Page<Parking> findAll(Pageable pageable) {
       return  jpaParkingRepository.findAll(pageable).map(ParkingEntity::toDomain);
    }
}
