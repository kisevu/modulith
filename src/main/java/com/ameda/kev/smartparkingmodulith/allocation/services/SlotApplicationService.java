package com.ameda.kev.smartparkingmodulith.allocation.services;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slot;
import com.ameda.kev.smartparkingmodulith.allocation.domain.repository.SlotRepository;
import com.ameda.kev.smartparkingmodulith.allocation.entity.SlotEntity;
import com.ameda.kev.smartparkingmodulith.allocation.vo.PublicId;
import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import com.ameda.kev.smartparkingmodulith.shared.events.VehicleParkingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Author: kev.Ameda
 */
@Service
@Transactional
public class SlotApplicationService {

    private final KafkaConsumer kafkaConsumer;

    private final SlotService slotService;
    private final Logger log = LoggerFactory.getLogger(SlotApplicationService.class);

    public SlotApplicationService(KafkaConsumer kafkaConsumer, SlotRepository slotRepository) {
        this.kafkaConsumer = kafkaConsumer;
        this.slotService = new SlotService(slotRepository);
    }



    @Transactional
    public Slot  createSlot(Slot slot){
        return slotService.createSlot(slot);
    }

    /**
    * It is a good practice to make use of async processing for long-running tasks.s
     * This helps to give timely feedback to the waiting user and provide substantial evidence of the
     * success of the process and a way for them to track the status of the process underway.
     * Spring boot provides for the @Async annotation, but in order to have the flexibility of providing the
     * queue capacity, the durability of the task in the queue, and the max pool size, we ought to create our
     * own ThreadPool task executor for that. It has been done in the shared folder
     *
     * @EventListener is synchronous in nature hence blocking the publishing thread until all listeners complete
     * However, I have included the @Async on top of it to make it asynchronous hence the publisher proceeds with other
     * tasks and does not block the publisher thread.
     *
     * There can only be two return types for where @Async is used and can be either void / CompletableFuture.
     * CompletableFuture return type is used where we need chaining of the result.
     *
     * N/B: In an event you are working in a transaction flow and using @Async, and you've created a ThreadPool.
     * Then make sure to put the @Async in a Transactional context as well.
     *
     * ------------------------------------------------------
     *
     *  N/B allocateSlot(VehicleParkingEvent event) takes use of @sync with @eventPublisher
     *
     *
     *  -------------------------------------------------------------------------------
     *
     *  Virtual Threads:
     *  Light-weight threads for handling high concurrency with minimal overhead. Virtual threads are instances of
     *  java.lang.Thread but are not tied to the o/s threads and the JVM schedules them onto available platform threads
     *  suspending them during blocking operations like I/O to free up resources.
     *  Spring boot supports virtual threads for @Async via auto-configuration. End game virtual concepts boosts
     *  performance in I/O heavy workloads.
    * */

    @Async(value = "asyncTaskExecutor")
    @EventListener
    public void allocateSlot(VehicleParkingEvent event){
        // find available slot to allocate0 = {Slot@14380}
        UUID eventPublicId = event.eventPublicId();
        VehicleEventObj eventObj = event.vehicleEventObj();
        List<Slot> availableSlots = slotService.findAvailableSlots();
        Optional<Slot> foundSlot = availableSlots.stream().findFirst();
        if (foundSlot.isPresent()){
            foundSlot.get().setAllocatedPerson(eventObj.ownerName());
            foundSlot.get().setAllocationTime(eventObj.entryTime());
            foundSlot.get().setReleaseTime(eventObj.exitTime());
            foundSlot.get().setVehicleRegNo(eventObj.vehicleRegNo());
            foundSlot.get().setAvailableSlot(false);
            foundSlot.get().setOwnerIdNo(eventObj.ownerIdNo());

            Slot slot = slotService.updateSlot(
                    foundSlot.get().getAllocatedPerson(),
                    foundSlot.get().getAllocationTime(),
                    foundSlot.get().getReleaseTime(),
                    foundSlot.get().getVehicleRegNo(),
                    foundSlot.get().getOwnerIdNo(),
                    foundSlot.get().getBlock(),
                    foundSlot.get().getSlot(),
                    foundSlot.get().getPublicId().publicId());
            if (slot != null ){
                log.info("Successfully allocated a slot: {}", slot.getSlot());
            }

            //see the executed thread per flow
            log.info("Updated the available marked slot : {}",Thread.currentThread().getName());
        }
    }



    @Transactional(readOnly = true)
    public Optional<Slot> getAslot(PublicId slotPublicId){
        return slotService.getASlot(slotPublicId);

    }

    @Transactional(readOnly = true)
    public Page<Slot> getSlots(Pageable pageable){
        return null;
    }

    @Transactional
    public SlotEntity assignParking(Blocks blockName, String person){
        List<SlotEntity> available = slotService.findAvailableSlotsByBlock(blockName);
        if (available.isEmpty()){
            throw new RuntimeException(" Block "+ blockName+ " is full");
        }
        SlotEntity slotEntity = available.get(0);
        slotService.allocateSlot(slotEntity.getId(),person);
        return slotEntity;
    }



    public void deallocate(Long slotId){
        Slot slot = slotService.findById(slotId).orElseThrow();
        slot.setAvailableSlot(true);
        slot.setAllocatedPerson(null);
        slotService.createSlot(slot);
    }

    public boolean isBlockFull(Blocks blockName){
        return slotService.countAvailableSlotsByBlock(blockName) == 0;
    }


}
