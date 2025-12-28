package com.ameda.kev.smartparkingmodulith.allocation.domain;

import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Builder
public class Slot {

    private String allocatedPerson;
    private Instant allocationTime;
    private Instant releaseTime;
    private PublicId publicId;
    private Long dbId;
    private Boolean availableSlot;
    private String vehicleRegNo;
    private String ownerName;
    private String ownerIdNo;
    private Blocks block;
    private Slots slot;
    private ABlock aBlock;

    public Slot() {
    }

    public Slot(String allocatedPerson, Instant allocationTime,
                Instant releaseTime, PublicId publicId,
                Long dbId, Boolean availableSlot, String vehicleRegNo,
                String ownerName,
                String ownerIdNo, Blocks block, Slots slot,
                ABlock aBlock) {
        this.allocatedPerson = allocatedPerson;
        this.allocationTime = allocationTime;
        this.releaseTime = releaseTime;
        this.publicId = publicId;
        this.dbId = dbId;
        this.availableSlot = true;
        this.vehicleRegNo = vehicleRegNo;
        this.ownerName = ownerName;
        this.ownerIdNo = ownerIdNo;
        this.block = block;
        this.slot = slot;
        this.aBlock = aBlock;
    }

    public void assertMandatoryFields(String allocatedPerson, Instant releaseTime){
        Assert.notNull("allocatedPerson",allocatedPerson);
        Assert.notNull("releaseTime",releaseTime);
    }

    public void initDefaultFields(){
        this.publicId = new PublicId(UUID.randomUUID());
        this.setAvailableSlot(true);
    }

    public String getAllocatedPerson() {
        return allocatedPerson;
    }

    public void setAllocatedPerson(String allocatedPerson) {
        this.allocatedPerson = allocatedPerson;
    }

    public Instant getAllocationTime() {
        return allocationTime;
    }

    public void setAllocationTime(Instant allocationTime) {
        this.allocationTime = allocationTime;
    }

    public Instant getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Instant releaseTime) {
        this.releaseTime = releaseTime;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(PublicId publicId) {
        this.publicId = publicId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public Boolean getAvailableSlot() {
        return availableSlot;
    }

    public void setAvailableSlot(Boolean availableSlot) {
        this.availableSlot = availableSlot;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdNo() {
        return ownerIdNo;
    }

    public void setOwnerIdNo(String ownerIdNo) {
        this.ownerIdNo = ownerIdNo;
    }

    public Blocks getBlock() {
        return block;
    }

    public void setBlock(Blocks block) {
        this.block = block;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public ABlock getaBlock() {
        return aBlock;
    }

    public void setaBlock(ABlock aBlock) {
        this.aBlock = aBlock;
    }
}
