package com.ameda.kev.smartparkingmodulith.allocation.domain;

import com.ameda.kev.smartparkingmodulith.allocation.vo.SlotPublicId;
import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;
import java.time.Instant;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Builder
public class Slot {

    private String allocatedPerson;
    private Instant allocationTime;
    private Instant releaseTime;
    private SlotPublicId publicId;
    private Long dbId;
    private Boolean availableSlot;
    private String vehicleRegNo;
    private String ownerName;
    private String ownerIdNo;

    public Slot() {
    }

    public Slot(String allocatedPerson, Instant allocationTime,
                Instant releaseTime, SlotPublicId publicId,
                Long dbId, Boolean availableSlot, String vehicleRegNo,
                String ownerName,
                String ownerIdNo) {
        this.allocatedPerson = allocatedPerson;
        this.allocationTime = allocationTime;
        this.releaseTime = releaseTime;
        this.publicId = publicId;
        this.dbId = dbId;
        this.availableSlot = true;
        this.vehicleRegNo = vehicleRegNo;
        this.ownerName = ownerName;
        this.ownerIdNo = ownerIdNo;
    }

    public void assertMandatoryFields(String allocatedPerson, Instant releaseTime){
        Assert.notNull("allocatedPerson",allocatedPerson);
        Assert.notNull("releaseTime",releaseTime);
    }

    public void initDefaultFields(){
        this.publicId = new SlotPublicId(UUID.randomUUID());
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

    public SlotPublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(SlotPublicId publicId) {
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
}
