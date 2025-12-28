package com.ameda.kev.smartparkingmodulith.allocation.domain;

import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntity;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Builder
public class ASlot {

    private Instant assignedTime;
    private Instant releaseTime;
    private Slots slot;
    private ABlockEntity block;
    private ParkingSlotEnum parkingSlotEnum;
    private Boolean slotTaken;
    private List<String> slotInitials;
    private PublicId publicId;

    public ASlot() {
    }

    public ASlot(Instant assignedTime, Instant releaseTime, Slots slot, ABlockEntity block,
                 ParkingSlotEnum parkingSlotEnum, Boolean slotTaken, List<String> slotInitials,
                 PublicId publicId) {
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.slot = slot;
        this.block = block;
        this.parkingSlotEnum = parkingSlotEnum;
        this.slotTaken = slotTaken;
        this.slotInitials = slotInitials;
        this.publicId = publicId;
    }

    public void initDefaultFields(){
        this.publicId = new PublicId(UUID.randomUUID());
        this.slotInitials = List.of("SLOTA","SLOTB","SLOTC","SLOTD");
    }


    public Instant getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Instant assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Instant getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Instant releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public ABlockEntity getBlock() {
        return block;
    }

    public void setBlock(ABlockEntity block) {
        this.block = block;
    }

    public ParkingSlotEnum getParkingSlotEnum() {
        return parkingSlotEnum;
    }

    public void setParkingSlotEnum(ParkingSlotEnum parkingSlotEnum) {
        this.parkingSlotEnum = parkingSlotEnum;
    }

    public Boolean getSlotTaken() {
        return slotTaken;
    }

    public void setSlotTaken(Boolean slotTaken) {
        this.slotTaken = slotTaken;
    }

    public List<String> getSlotInitials() {
        return slotInitials;
    }

    public void setSlotInitials(List<String> slotInitials) {
        this.slotInitials = slotInitials;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(PublicId publicId) {
        this.publicId = publicId;
    }
}
