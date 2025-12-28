package com.ameda.kev.smartparkingmodulith.allocation.repository;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Repository
public interface JpaSlotRepository extends JpaRepository<SlotEntity, Long> {

    Optional<SlotEntity> findByPublicId(UUID publicId);

    @Modifying
    @Query("UPDATE SlotEntity s SET " +
            "s.allocatedPerson = :ownerName, " +
            "s.allocationTime = :entryTime, " +
            "s.releaseTime = :exitTime, " +
            "s.vehicleRegNo = :vehicleRegNo, " +
            "s.ownerName = :ownerName, " +
            "s.ownerIdNo = :ownerIdNo, " +
            "s.block = :block, " +
            "s.slot = :slot " +
            "WHERE s.availableSlot = true" )
    void updateSlot(@Param("ownerName") String ownerName,
                    @Param("entryTime") Instant entryTime,
                    @Param("exitTime") Instant exitTime,
                    @Param("vehicleRegNo") String vehicleRegNo,
                    @Param("ownerIdNo") String ownerIdNo,
                    @Param("block") Blocks block,
                    @Param("slot") Slots slot,
                    @Param("publicId") UUID publicId);


    @Query("SELECT s FROM SlotEntity s WHERE s.availableSlot = true")
    List<SlotEntity> findAllAvailableSlots();

    @Query("SELECT s FROM SlotEntity s WHERE s.ownerIdNo = :ownerIdNo")
    Optional<SlotEntity> findByOwnerIdNo(@Param("ownerIdNo") String ownerIdNo);

    @Query("SELECT COUNT(s) FROM SlotEntity s WHERE s.block = :block AND s.allocatedPerson IS NOT NULL")
    long countOccupiedByBlock(@Param("block") Blocks block);


}
