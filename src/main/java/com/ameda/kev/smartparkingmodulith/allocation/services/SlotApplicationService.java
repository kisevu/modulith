package com.ameda.kev.smartparkingmodulith.allocation.services;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.SlotBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.domain.repository.SlotRepository;
import com.ameda.kev.smartparkingmodulith.allocation.exceptions.SlotNotFoundException;
import com.ameda.kev.smartparkingmodulith.allocation.vo.SlotPublicId;
import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import com.ameda.kev.smartparkingmodulith.shared.events.VehicleParkingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Service
public class SlotApplicationService {

    private final SlotService slotService;

    public SlotApplicationService(SlotRepository slotRepository) {
        this.slotService = new SlotService(slotRepository);
    }

    public Slot  createSlot(Slot slot){
        return slotService.createSlot(slot);
    }

    @EventListener
    public void allocateSlot(VehicleParkingEvent event){
        // find available slot to allocate
        UUID eventPublicId = event.eventPublicId();
        VehicleEventObj eventObj = event.vehicleEventObj();
        List<Slot> availableSlots = slotService.findAvailableSlots();
        Optional<Slot> foundSlot = availableSlots.stream().findFirst();
        if (foundSlot.isPresent()){
            foundSlot.get().setAllocatedPerson(eventObj.ownerName());
            foundSlot.get().setAllocationTime(eventObj.entryTime());
            foundSlot.get().setReleaseTime(eventObj.exitTime());
            foundSlot.get().setVehicleRegNo(eventObj.vehicleRegNo());
            foundSlot.get().setOwnerIdNo(eventObj.ownerIdNo());
        }
        Slot slot = slotService.updateSlot(
                foundSlot.get().getAllocatedPerson(),
                foundSlot.get().getAllocationTime(),
                foundSlot.get().getReleaseTime(),
                foundSlot.get().getVehicleRegNo(),
                foundSlot.get().getOwnerIdNo(),
                foundSlot.get().getPublicId().publicId());
    }


    public Optional<Slot> getAslot(SlotPublicId  slotPublicId){
        return slotService.getASlot(slotPublicId);

    }

    public Page<Slot> getSlots(Pageable pageable){
        return null;
    }
}
