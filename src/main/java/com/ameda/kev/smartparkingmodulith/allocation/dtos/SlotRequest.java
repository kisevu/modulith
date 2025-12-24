package com.ameda.kev.smartparkingmodulith.allocation.dtos;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.SlotBuilder;
import org.jilt.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Builder
public record SlotRequest(String allocatedPerson, Instant allocated, Instant releaseTime, UUID publicId, Long dbId, Boolean availableSlot, String vehicleRegNo,
                          String ownerName,
                          String ownerIdNo
                          ) {

    public static SlotRequest from(Slot slot){
     return  SlotRequestBuilder.slotRequest()
               .allocatedPerson(slot.getAllocatedPerson())
               .allocated(slot.getAllocationTime())
               .releaseTime(slot.getReleaseTime())
               .publicId(slot.getPublicId().publicId())
               .dbId(slot.getDbId())
               .availableSlot(slot.getAvailableSlot())
               .vehicleRegNo(slot.getVehicleRegNo())
               .ownerName(slot.getOwnerName())
               .ownerIdNo(slot.getOwnerIdNo())
               .build();
    }

    public static Slot to(SlotRequest slotRequest){
        return SlotBuilder.slot()
                .allocationTime(slotRequest.allocated())
                .releaseTime(slotRequest.releaseTime())
                .allocatedPerson(slotRequest.allocatedPerson())
                .dbId(slotRequest.dbId())
                .availableSlot(slotRequest.availableSlot())
                .vehicleRegNo(slotRequest.vehicleRegNo())
                .ownerName(slotRequest.ownerName())
                .ownerIdNo(slotRequest.ownerIdNo())
                .build();
    }
}
