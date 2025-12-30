package com.ameda.kev.smartparkingmodulith.entry.repository;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
public interface ParkingRepository {
    Parking save(Parking parking);
    Optional<Parking> findByPublicId(UUID publicId);
    Page<Parking> findAll(Pageable pageable);

    Optional<Parking> findById(Long id);
}
