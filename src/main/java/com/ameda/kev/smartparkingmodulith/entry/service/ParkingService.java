package com.ameda.kev.smartparkingmodulith.entry.service;

import com.ameda.kev.smartparkingmodulith.entry.domain.Parking;
import com.ameda.kev.smartparkingmodulith.entry.repository.ParkingRepository;
import com.ameda.kev.smartparkingmodulith.entry.vo.VehiclePublicId;
import com.ameda.kev.smartparkingmodulith.shared.domain.VehicleEventObj;
import com.ameda.kev.smartparkingmodulith.shared.events.VehicleParkingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Author: kev.Ameda
 */
@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final String topicName;

    private final Logger log = LoggerFactory.getLogger(ParkingService.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public ParkingService(ParkingRepository parkingRepository, KafkaTemplate<String, String> kafkaTemplate,
                          ObjectMapper objectMapper, @Value("${parking.assign.slot.topic}") String topicName) {
        this.parkingRepository = parkingRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topicName = topicName;
    }

    public Parking createParking(Parking parking){
        parking.initFieldOnCreation();
        parking.setLastSeen(Instant.now());
        Parking savedParking = parkingRepository.save(parking);
        eventPublisher.publishEvent(new VehicleParkingEvent(UUID.randomUUID(),
                new VehicleEventObj(savedParking.getOwnerName(),
                        savedParking.getEntryTime(),
                        savedParking.getExitTime(),
                        savedParking.getVehicleRegNo(),
                        savedParking.getOwnerIdNo())));

        return savedParking;
    }




    public Optional<Parking> getParkingByPublicId(VehiclePublicId vehiclePublicId){
        return parkingRepository.findByPublicId(vehiclePublicId.value());
    }

    public Page<Parking> getAllParking(Pageable pageable){
        return parkingRepository.findAll(pageable);
    }

    /**
     * Unleashing the power of multi-threading
     * So here we are giving each batch to multi-threading
    * */


    private List<List<Long>> splitIntoBatches(List<Long> publicIds, int batchSize){
        int totalSize = publicIds.size();

        int batchNums = (totalSize + batchSize - 1) / batchSize;

        List<List<Long>> batches = new ArrayList<>();
        for(int i = 0; i < batchNums; i++){
            int start = i * batchSize;
            int end = Math.min(totalSize, (i+1) * batchSize);
            batches.add(publicIds.subList(start,end));
        }
        return batches;
    }

    private void executePublicIds(List<Long> publicIds){
        List<List<Long>> batches = splitIntoBatches(publicIds, 50);
        List<CompletableFuture<Void>> completableFutures = batches.stream()
                .map(batch -> CompletableFuture.runAsync(() -> processBatches(batch)))
                .collect(Collectors.toList());
        // wait for all future to complete
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .join();
    }

    private void processBatches(List<Long> ids) {
        ids.parallelStream()
                .forEach(this::fetchAndPublish);
    }

    public void fetchAndPublish( Long id ){
        Optional<Parking> parking =  findById(id);
        if (parking.isPresent()){
            try{
                String json = objectMapper.writeValueAsString(parking.get());
                kafkaTemplate.send(topicName,json);
            }catch (JsonProcessingException ex){
                log.error("Error occurred : {} and cause : {}",ex.getMessage(),ex.getCause());
            }
        }

    }

    private Optional<Parking> findById(Long id) {
        return parkingRepository.findById(id);
    }

    /*
    *  TODOs:
    *  - Request for a slot / ticket
    *  - Persist the vehicle
    *  - Allocate a parking slot
    *  - Send notification to user
    *  - Initiate payment
    *  - Send notification to acknowledge payment
    *  - free up the previous located space and add to the request queue
    * */


}
