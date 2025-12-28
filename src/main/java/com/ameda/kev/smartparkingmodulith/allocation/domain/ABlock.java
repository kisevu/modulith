package com.ameda.kev.smartparkingmodulith.allocation.domain;

import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntity;
import com.ameda.kev.smartparkingmodulith.allocation.entity.ABlockEntityBuilder;
import com.ameda.kev.smartparkingmodulith.allocation.entity.ASlotEntity;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import org.jilt.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.RecursiveTask;

/**
 * Author: kev.Ameda
 */

@Builder
public class ABlock {

    private Instant assignedTime;
    private Instant releaseTime;
    private Blocks block;
    private ParkingSlotEnum parkingSlotEnum;
    private Set<String> blockInitials;
    private List<ASlot> slots = new ArrayList<>();
    private PublicId publicId;

    public ABlock() {
    }

    public ABlock(Instant assignedTime, Instant releaseTime,
                  Blocks block, ParkingSlotEnum parkingSlotEnum,
                  Set<String> blockInitials,
                  List<ASlot> slots, PublicId publicId) {
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
        this.block = block;
        this.parkingSlotEnum = parkingSlotEnum;
        this.blockInitials = blockInitials;
        this.slots = slots;
        this.publicId = publicId;
    }

    public  void initDefaultFields(){
        this.publicId = new PublicId(UUID.randomUUID());
        this.blockInitials = Set.of("BLOCKA","BLOCKB","BLOCKC");
    }

    public static ABlock toDomain(ABlockEntity aBlockEntity){
        return ABlockBuilder.aBlock()
                .assignedTime(aBlockEntity.getAssignedTime())
                .releaseTime(aBlockEntity.getReleaseTime())
                .block(aBlockEntity.getBlock())
                .parkingSlotEnum(aBlockEntity.getParkingStatus())
                .blockInitials(aBlockEntity.getBlockInitials())
                .slots(aBlockEntity.getSlots().stream().map(ASlotEntity::toDomain).toList())
                .publicId(new PublicId(aBlockEntity.getPublicId()))
                .build();
    }

    public static ABlockEntity fromDomain(ABlock aBlock){
        return ABlockEntityBuilder.aBlockEntity()
                .assignedTime(aBlock.getAssignedTime())
                .releaseTime(aBlock.getReleaseTime())
                .block(aBlock.getBlock())
                .parkingStatus(aBlock.getParkingSlotEnum())
                .blockInitials(aBlock.getBlockInitials())
                .slots(aBlock.getSlots().stream().map(ASlotEntity::fromDomain).toList())
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

    public ParkingSlotEnum getParkingSlotEnum() {
        return parkingSlotEnum;
    }

    public void setParkingSlotEnum(ParkingSlotEnum parkingSlotEnum) {
        this.parkingSlotEnum = parkingSlotEnum;
    }

    public Set<String> getBlockInitials() {
        return blockInitials;
    }

    public void setBlockInitials(Set<String> blockInitials) {
        this.blockInitials = blockInitials;
    }

    public List<ASlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ASlot> slots) {
        this.slots = slots;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(PublicId publicId) {
        this.publicId = publicId;
    }
}