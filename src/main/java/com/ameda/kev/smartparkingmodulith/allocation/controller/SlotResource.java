package com.ameda.kev.smartparkingmodulith.allocation.controller;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.dtos.SlotRequest;
import com.ameda.kev.smartparkingmodulith.allocation.dtos.SlotResponse;
import com.ameda.kev.smartparkingmodulith.allocation.services.SlotApplicationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/api/slot")
public class SlotResource {

    private final SlotApplicationService slotApplicationService;

    public SlotResource(SlotApplicationService slotApplicationService) {
        this.slotApplicationService = slotApplicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<SlotResponse> createSlots(@RequestBody SlotRequest slotRequest){
        Slot slotReq = SlotRequest.to(slotRequest);
        Slot createdSlot = slotApplicationService.createSlot(slotReq);
        SlotResponse slotResponse = SlotResponse.from(createdSlot);
        if( slotResponse == null ){
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(slotResponse);
    }
}
