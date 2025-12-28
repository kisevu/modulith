package com.ameda.kev.smartparkingmodulith.allocation.entity;

import com.ameda.kev.smartparkingmodulith.allocation.domain.ASlot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.ASlotBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.domain.ParkingSlotEnum;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import com.ameda.kev.smartparkingmodulith.shared.entity.AbstractAuditing;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Entity
@Builder
@Table(name = "tbl_slot_slots")
public class ASlotEntity extends AbstractAuditing<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aslotSequenceGenerator")
    @SequenceGenerator(name = "aslotSequenceGenerator", sequenceName = "aslot_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "assigned_time")
    private Instant assignedTime;

    @Column(name = "release_time")
    private Instant releaseTime;

    @Column(name = "slot_assigned")
    @Enumerated(EnumType.STRING)
    private Slots slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private ABlockEntity block;

    @Column(name = "parking_status")
    @Enumerated(EnumType.STRING)
    private ParkingSlotEnum parkingStatus;

    @Column(name = "slot_taken")
    private Boolean slotTaken;

    @Column(name = "slot_initials")
    private List<String> slotInitials;

    @Column(name = "public_id")
    private UUID publicId;

    public ASlotEntity() {
    }

    public ASlotEntity(Long id, Instant assignedTime, Instant releaseTime, Slots slot,
                       ABlockEntity block, ParkingSlotEnum parkingStatus,
                       Boolean slotTaken, List<String> slotInitials, UUID publicId) {
        this.id = id;
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.slot = slot;
        this.block = block;
        this.parkingStatus = parkingStatus;
        this.slotTaken = slotTaken;
        this.slotInitials = slotInitials;
        this.publicId = publicId;
    }

    public static ASlotEntity fromDomain(ASlot aSlot){
        return ASlotEntityBuilder.aSlotEntity()
                .assignedTime(aSlot.getAssignedTime())
                .releaseTime(aSlot.getReleaseTime())
                .slot(aSlot.getSlot())
                .block(aSlot.getBlock())
                .parkingStatus(aSlot.getParkingSlotEnum())
                .slotTaken(aSlot.getSlotTaken())
                .slotInitials(aSlot.getSlotInitials())
                .publicId(aSlot.getPublicId().publicId())
                .build();
    }

    public static ASlot toDomain(ASlotEntity aSlotEntity){
        return ASlotBuilder.aSlot()
                .assignedTime(aSlotEntity.getAssignedTime())
                .releaseTime(aSlotEntity.getReleaseTime())
                .slot(aSlotEntity.getSlot())
                .block(aSlotEntity.getBlock())
                .parkingSlotEnum(aSlotEntity.getParkingStatus())
                .slotInitials(aSlotEntity.getSlotInitials())
                .publicId(new PublicId(aSlotEntity.publicId))
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ParkingSlotEnum getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingSlotEnum parkingStatus) {
        this.parkingStatus = parkingStatus;
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

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ASlotEntity that)) return false;
        return Objects.equals(getPublicId(), that.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPublicId());
    }
}
