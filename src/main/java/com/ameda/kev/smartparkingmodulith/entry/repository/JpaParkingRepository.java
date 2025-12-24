package com.ameda.kev.smartparkingmodulith.entry.repository;

import com.ameda.kev.smartparkingmodulith.entry.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Repository
public interface JpaParkingRepository extends JpaRepository<ParkingEntity,Long> {
    Optional<ParkingEntity> findByPublicId(UUID publicId);
}
