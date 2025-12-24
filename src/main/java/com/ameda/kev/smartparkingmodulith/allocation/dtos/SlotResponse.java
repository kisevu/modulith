package com.ameda.kev.smartparkingmodulith.allocation.dtos;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.exceptions.SlotNotFoundException;
import org.jilt.Builder;

/**
 * Author: kev.Ameda
 */
@Builder
public record SlotResponse(String message, SlotStatus status) {

    public static SlotResponse from(Slot slot){
        SlotResponseBuilder slotResponseBuilder = SlotResponseBuilder.slotResponse();
        if(slot != null){
            slotResponseBuilder.message("Slot has been created successfully...");
            slotResponseBuilder.status(SlotStatus.SUCCESS);
            return slotResponseBuilder.build();
        } else {
            slotResponseBuilder.message("No slot has been created...");
            slotResponseBuilder.status(SlotStatus.FAILED);
           return  slotResponseBuilder.build();
        }
    }
}
