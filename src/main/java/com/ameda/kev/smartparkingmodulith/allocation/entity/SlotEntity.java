package com.ameda.kev.smartparkingmodulith.allocation.entity;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.SlotBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.vo.SlotPublicId;
import com.ameda.kev.smartparkingmodulith.shared.entity.AbstractAuditing;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Entity
@Builder
@Table(name = "tbl_slots")
public class SlotEntity extends AbstractAuditing<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slotSequenceGenerator")
    @SequenceGenerator(name = "slotSequenceGenerator", sequenceName = "slot_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "allocated_person")
    private String allocatedPerson;
    @Column(name = "allocation_time")
    private Instant allocationTime;
    @Column(name ="release_time")
    private Instant releaseTime;

    @Column( name = "public_id")
    private UUID publicId;

    @Column(name = "available_slot")
    private Boolean availableSlot;

    @Column(name = "vehicle_reg_no")
    private String vehicleRegNo;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_id_no")
    private String ownerIdNo;

    public SlotEntity() {
    }

    public SlotEntity(Long id, String allocatedPerson, Instant allocationTime, Instant releaseTime, UUID publicId,
                      Boolean availableSlot,
                      String vehicleRegNo,
                      String ownerIdNo,
                      String ownerName) {
        this.id = id;
        this.allocatedPerson = allocatedPerson;
        this.allocationTime = allocationTime;
        this.releaseTime = releaseTime;
        this.publicId = publicId;
        this.availableSlot = availableSlot;
        this.vehicleRegNo = vehicleRegNo;
        this.ownerName = ownerName;
        this.ownerIdNo = ownerIdNo;
    }

    public static Slot fromDomain(SlotEntity slotEntity){
       return SlotBuilder.slot()
               .allocatedPerson(slotEntity.getAllocatedPerson())
               .allocationTime(slotEntity.getAllocationTime())
               .releaseTime(slotEntity.getReleaseTime())
               .publicId(new SlotPublicId(slotEntity.getPublicId()))
               .dbId(slotEntity.getId())
               .availableSlot(true)
               .vehicleRegNo(slotEntity.getVehicleRegNo())
               .ownerName(slotEntity.getOwnerName())
               .ownerIdNo(slotEntity.getOwnerIdNo())
               .build();
    }

    public static SlotEntity toDomain(Slot slot){
        return SlotEntityBuilder.slotEntity()
                .allocatedPerson(slot.getAllocatedPerson())
                .allocationTime(slot.getAllocationTime())
                .releaseTime(slot.getReleaseTime())
                .publicId(slot.getPublicId().publicId())
                .availableSlot(slot.getAvailableSlot())
                .vehicleRegNo(slot.getVehicleRegNo())
                .ownerName(slot.getOwnerName())
                .ownerIdNo(slot.getOwnerIdNo())
                .build();
    }

    public String getOwnerIdNo() {
        return ownerIdNo;
    }

    public void setOwnerIdNo(String ownerIdNo) {
        this.ownerIdNo = ownerIdNo;
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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SlotEntity that)) return false;
        return Objects.equals(getPublicId(), that.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPublicId());
    }
}
