package com.ameda.kev.smartparkingmodulith.allocation.repository;

import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: kev.Ameda
 */
@Repository
public interface JpaABlockRepository extends JpaRepository<ABlockEntity,Long> {
}
