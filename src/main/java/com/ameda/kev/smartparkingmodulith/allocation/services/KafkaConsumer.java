package com.ameda.kev.smartparkingmodulith.allocation.services;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.ameda.kev.smartparkingmodulith.allocation.domain.repository.SlotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;



/**
 * Author: kev.Ameda
 */
@Service
public class KafkaConsumer {

    @Value("${parking.assign.slot.topic}")
    private String topicName;

    private final ObjectMapper objectMapper;
    private final SlotService slotService;


    private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaConsumer(ObjectMapper objectMapper, SlotRepository slotRepository) {
        this.objectMapper = objectMapper;
        this.slotService = new SlotService(slotRepository);
    }

//    @KafkaListener(topics = "${parking.assign.slot.topic}")
//    public void consumeBlockNSlotsInfo(String json){
//        try{
//         Map<String, List<String>> layout = objectMapper.readValue(json, Map.class);
//         log.info("Data that came: {}", layout);
//         slotService.updateSlot()
//
//     }catch (JsonProcessingException ex){
//         log.error(" Error occurred: {} with cause: {}",ex.getMessage(),ex.getCause());
//     }
//    }

    @KafkaListener(topics = "${parking.assign.slot.topic}")
    public void consumeBlockNSlotsInfo(String json) {
        try {
            Map<String, List<String>> layout = objectMapper.readValue(json, new TypeReference<Map<String, List<String>>>() {});
            log.info("Received block-slot layout: {}", layout);

            // Iterate through each block and its assigned slots
            layout.forEach((blockName, slotNames) -> {
                Blocks block = Blocks.valueOf(blockName);
                slotNames.forEach(slotName -> {
                    Slots slot = Slots.valueOf(slotName);
                    slotService.updateSlot(block, slot);
                    log.debug("Updated slot {} in block {}", slotName, blockName);
                    log.info("updated the block and slot");
                });
            });

        } catch (JsonProcessingException ex) {
            log.error("JSON parsing error: {} with cause: {}", ex.getMessage(), ex.getCause());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid enum value in message: {}", ex.getMessage());
        }
    }

}
