package com.ameda.kev.smartparkingmodulith.allocation.entity;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.ParkingSlotEnum;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import com.ameda.kev.smartparkingmodulith.shared.entity.AbstractAuditing;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;

/**
 * Author: kev.Ameda
 */
@Builder
@Entity
@Table(name = "tbl_slot_blocks")
public class ABlockEntity extends AbstractAuditing<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ablockSequenceGenerator")
    @SequenceGenerator(name = "ablockSequenceGenerator", sequenceName = "ablock_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "assigned_time")
    private Instant assignedTime;

    @Column(name = "release_time")
    private Instant releaseTime;

    @Column(name = "block_assigned")
    @Enumerated(EnumType.STRING)
    private Blocks block;

    @Column(name = "parking_status")
    @Enumerated(EnumType.STRING)
    private ParkingSlotEnum parkingStatus;

    @Column(name = "blocks_available")
    private Set<String> blockInitials;

    @OneToMany(mappedBy = "block", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ASlotEntity> slots = new ArrayList<>();

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;

    public ABlockEntity() {
    }


    public ABlockEntity(Long id, Instant assignedTime, Instant releaseTime, Blocks block,
                        ParkingSlotEnum parkingStatus, Set<String> blockInitials, List<ASlotEntity> slots, UUID publicId) {
        this.id = id;
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.block = block;
        this.parkingStatus = parkingStatus;
        this.blockInitials = blockInitials;
        this.slots = slots;
        this.publicId = publicId;
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

    public Blocks getBlock() {
        return block;
    }

    public void setBlock(Blocks block) {
        this.block = block;
    }

    public ParkingSlotEnum getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingSlotEnum parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public Set<String> getBlockInitials() {
        return blockInitials;
    }

    public void setBlockInitials(Set<String> blockInitials) {
        this.blockInitials = blockInitials;
    }

    public List<ASlotEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<ASlotEntity> slots) {
        this.slots = slots;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ABlockEntity that)) return false;
        return Objects.equals(getPublicId(), that.getPublicId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPublicId());
    }
}
