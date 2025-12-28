package com.ameda.kev.smartparkingmodulith.allocation.domain.repository;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
public interface SlotRepository {

    public Slot create(Slot slot);
    public Optional<Slot> getSlot(UUID publicId);
    Page<Slot> getSlots(Pageable pageable);
    Optional<Slot> updateSlot(String ownerName,
                              Instant entryTime,
                              Instant exitTime,
                              String vehicleRegNo,
                              String ownerIdNo,
                              Blocks block,
                              Slots slot,
                              UUID publicId);
    List<Slot> findAvailableSlots();
    Optional<Slot> findByOwnerIdNo(@Param("ownerIdNo") String ownerIdNo);
    long countOccupiedByBlock(Blocks block);
}
