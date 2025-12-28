package com.ameda.kev.smartparkingmodulith.allocation.domain.repository;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import com.ameda.kev.smartparkingmodulith.allocation.exceptions.SlotNotFoundException;
import com.ameda.kev.smartparkingmodulith.allocation.repository.JpaSlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@Repository
public class SpringDataSlotRepository implements SlotRepository{
    private final JpaSlotRepository jpaSlotRepository;

    public SpringDataSlotRepository(JpaSlotRepository jpaSlotRepository) {
        this.jpaSlotRepository = jpaSlotRepository;
    }

    @Override
    public Slot create(Slot slot) {
        SlotEntity slotEntity = SlotEntity.toDomain(slot);
        // create all blocks with individual slots in there
        SlotEntity savedSlotEntity = jpaSlotRepository.save(slotEntity);
        Slot repoSlot = SlotEntity.fromDomain(savedSlotEntity);
        return repoSlot;
    }


    @Override
    public Optional<Slot> getSlot(UUID publicId) {
        return jpaSlotRepository.findByPublicId(publicId).map(SlotEntity::fromDomain);
    }

    @Override
    public Page<Slot> getSlots(Pageable pageable) {
        return null;
    }



    @Override
    public Optional<Slot> updateSlot(String ownerName, Instant entryTime, Instant exitTime, String vehicleRegNo, String ownerIdNo, Blocks block, Slots slot, UUID publicId) {
        jpaSlotRepository.updateSlot(ownerName, entryTime, exitTime, vehicleRegNo, ownerIdNo,block,slot, publicId);
        Optional<Slot> byOwnerIdNo = this.findByOwnerIdNo(ownerIdNo);

        if (byOwnerIdNo.isPresent()){
            return byOwnerIdNo;
        }
        throw new SlotNotFoundException("Could not find Slot with owner-id-no: "+ ownerIdNo);
    }

    @Override
    public List<Slot> findAvailableSlots() {
        return jpaSlotRepository.findAllAvailableSlots()
                .stream()
                .map(SlotEntity::fromDomain).toList();
    }

    @Override
    public Optional<Slot> findByOwnerIdNo(String ownerIdNo) {
        return jpaSlotRepository.findByOwnerIdNo(ownerIdNo).map(SlotEntity::fromDomain);
    }

    @Override
    public long countOccupiedByBlock(Blocks block) {
        return jpaSlotRepository.countOccupiedByBlock(block);
    }
}
