package com.ameda.kev.smartparkingmodulith.allocation.repository;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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

    @Query("SELECT s FROM SlotEntity s WHERE s.block =: blockName AND s.availableSlot = true")
    List<SlotEntity> findAvailableSlotsByBlock(@Param("blockName") Blocks blockName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COUNT(s) FROM SlotEntity s WHERE s.block =: blockName AND s.availableSlot")
    int countAvailableSlotsByBlock(@Param("blockName") Blocks blockName);

    @Modifying
    @Query("UPDATE SlotEntity s SET s.availableSlot = false, s.allocatedPerson =: person  WHERE s.id =: slotId")
    void allocateSlot(@Param("slotId") Long slotId, @Param("person") String person);

    @Modifying
    @Query("UPDATE SlotEntity s SET s.block = :blockName, s.slot = :slotName " +
            "WHERE s.block IS NULL AND s.slot IS NULL")
    void update(@Param("blockName") Blocks blockName,
                         @Param("slotName") Slots slotName);
}
