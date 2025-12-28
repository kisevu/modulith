package com.ameda.kev.smartparkingmodulith.allocation.entity;

import com.ameda.kev.smartparkingmodulith.allocation.domain.ABlock;
import com.ameda.kev.smartparkingmodulith.allocation.domain.ABlockBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
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
@Table(name = "tbl_blocks")
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

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SlotEntity> slots = new ArrayList<>();

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;

    @Transient
    public boolean isFull(){
        return getAvailableSlots().isEmpty();
    }

    public List<SlotEntity> getAvailableSlots(){
        return slots.stream().filter(SlotEntity::getAvailableSlot).toList();
    }

    public ABlockEntity() {
    }


    public ABlockEntity(Long id, Instant assignedTime, Instant releaseTime, Blocks block
                    ,List<SlotEntity> slots, UUID publicId) {
        this.id = id;
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.block = block;
        this.slots = slots;
        this.publicId = publicId;
    }


    public static ABlockEntity fromDomain(ABlock aBlock){
        return ABlockEntityBuilder.aBlockEntity()
                .assignedTime(aBlock.getAssignedTime())
                .releaseTime(aBlock.getReleaseTime())
                .block(aBlock.getBlock())
                .slots(aBlock.getSlots().stream().map(SlotEntity::toDomain).toList())
                .publicId(aBlock.getPublicId().publicId())
                .build();
    }

    public static ABlock toDomain(ABlockEntity aBlockEntity){
        return ABlockBuilder.aBlock()
                .assignedTime(aBlockEntity.getAssignedTime())
                .releaseTime(aBlockEntity.getReleaseTime())
                .block(aBlockEntity.getBlock())
                .slots(aBlockEntity.getSlots().stream().map(SlotEntity::fromDomain).toList())
                .publicId(new PublicId(aBlockEntity.getPublicId()))
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

    public Blocks getBlock() {
        return block;
    }

    public void setBlock(Blocks block) {
        this.block = block;
    }

    public List<SlotEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotEntity> slots) {
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
