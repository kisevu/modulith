package com.ameda.kev.smartparkingmodulith.allocation.domain;

import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntity;
import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntityBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import org.jilt.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */

@Builder
public class ABlock {

    private Instant assignedTime;
    private Instant releaseTime;
    private Blocks block;
    private List<Slot> slots = new ArrayList<>();
    private PublicId publicId;

    public ABlock() {
    }

    public ABlock(Instant assignedTime, Instant releaseTime,
                  Blocks block,
                  List<Slot> slots, PublicId publicId) {
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.block = block;
        this.slots = slots;
        this.publicId = publicId;
    }

    public  void initDefaultFields(){
        this.publicId = new PublicId(UUID.randomUUID());
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

    public static ABlockEntity fromDomain(ABlock aBlock){
        return ABlockEntityBuilder.aBlockEntity()
                .assignedTime(aBlock.getAssignedTime())
                .releaseTime(aBlock.getReleaseTime())
                .block(aBlock.getBlock())
                .slots(aBlock.getSlots().stream().map(SlotEntity::toDomain).toList())
                .publicId(aBlock.getPublicId().publicId())
                .build();
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
    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(PublicId publicId) {
        this.publicId = publicId;
    }
}