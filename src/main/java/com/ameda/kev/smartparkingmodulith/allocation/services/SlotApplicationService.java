package com.ameda.kev.smartparkingmodulith.allocation.services;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.repository.SlotRepository;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import com.ameda.kev.smartparkingmodulith.shared.events.VehicleParkingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * Author: kev.Ameda
 */
@Service
public class SlotApplicationService {

    private final SlotService slotService;
    private final Logger log = LoggerFactory.getLogger(SlotApplicationService.class);

    public SlotApplicationService(SlotRepository slotRepository) {
        this.slotService = new SlotService(slotRepository);
    }

    @Transactional
    public Slot  createSlot(Slot slot){
        return slotService.createSlot(slot);
    }

    @EventListener
    public void allocateSlot(VehicleParkingEvent event){
        // find available slot to allocate0 = {Slot@14380}
        UUID eventPublicId = event.eventPublicId();
        VehicleEventObj eventObj = event.vehicleEventObj();
        List<Slot> availableSlots = slotService.findAvailableSlots();
        Optional<Slot> foundSlot = availableSlots.stream().findFirst();
        if (foundSlot.isPresent()){
            foundSlot.get().setAllocatedPerson(eventObj.ownerName());
            foundSlot.get().setAllocationTime(eventObj.entryTime());
            foundSlot.get().setReleaseTime(eventObj.exitTime());
            foundSlot.get().setVehicleRegNo(eventObj.vehicleRegNo());
            foundSlot.get().setAvailableSlot(false);
            foundSlot.get().setOwnerIdNo(eventObj.ownerIdNo());

            Slot slot = slotService.updateSlot(
                    foundSlot.get().getAllocatedPerson(),
                    foundSlot.get().getAllocationTime(),
                    foundSlot.get().getReleaseTime(),
                    foundSlot.get().getVehicleRegNo(),
                    foundSlot.get().getOwnerIdNo(),
                    foundSlot.get().getBlock(),
                    foundSlot.get().getSlot(),
                    foundSlot.get().getPublicId().publicId());
            if (slot != null ){
                log.info("Successfully allocated a slot: {}", slot.getSlot());
            }
        }
    }

    @Transactional(readOnly = true)
    public Optional<Slot> getAslot(PublicId slotPublicId){
        return slotService.getASlot(slotPublicId);

    }

    @Transactional(readOnly = true)
    public Page<Slot> getSlots(Pageable pageable){
        return null;
    }

    @Transactional
    public SlotEntity assignParking(Blocks blockName, String person){
        List<SlotEntity> available = slotService.findAvailableSlotsByBlock(blockName);
        if (available.isEmpty()){
            throw new RuntimeException(" Block "+ blockName+ " is full");
        }
        SlotEntity slotEntity = available.get(0);
        slotService.allocateSlot(slotEntity.getId(),person);
        return slotEntity;
    }

    public void deallocate(Long slotId){
        Slot slot = slotService.findById(slotId).orElseThrow();
        slot.setAvailableSlot(true);
        slot.setAllocatedPerson(null);
        slotService.createSlot(slot);
    }

    public boolean isBlockFull(Blocks blockName){
        return slotService.countAvailableSlotsByBlock(blockName) == 0;
    }

}
