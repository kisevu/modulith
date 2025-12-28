package com.ameda.kev.smartparkingmodulith.allocation.services;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.domain.repository.SlotRepository;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

public class SlotService {
    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }


    public Slot createSlot(Slot slot){
        slot.initDefaultFields();
        return slotRepository.create(slot);
    }

    public Optional<Slot> getASlot(PublicId slotPublicId){
        return slotRepository.getSlot(slotPublicId.publicId());
    }

    public Page<Slot> getSlots(Pageable pageable){
        return null;
    }

    public Slot updateSlot(String name, Instant allocationTime, Instant releaseTime,
                           String vehicleRegNo, String ownerIdNo,
                           Blocks block, Slots slots, UUID publicId){
        Slot slot = slotRepository
                .updateSlot(name, allocationTime, releaseTime, vehicleRegNo, ownerIdNo, block,slots,publicId)
                .stream()
                .findFirst()
                .get();
        return slot;
    }

    public List<Slot> findAvailableSlots(){
        return slotRepository.findAvailableSlots();
    }

    public Long countByOccupiedBlock(Blocks block){
        return slotRepository.countOccupiedByBlock(block);
    }
}
