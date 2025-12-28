package com.ameda.kev.smartparkingmodulith.allocation.repository;

import com.ameda.kev.smartparkingmodulith.allocation.entity.ASlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: kev.Ameda
 */
@Repository
public interface JpaASlotRepository extends JpaRepository<ASlotEntity,Long> {
}
