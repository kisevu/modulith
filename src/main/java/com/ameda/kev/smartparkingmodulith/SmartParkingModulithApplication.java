package com.ameda.kev.smartparkingmodulith;

import com.ameda.kev.smartparkingmodulith.allocation.domain.Blocks;
import com.ameda.kev.smartparkingmodulith.allocation.domain.Slots;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;
import java.util.stream.Collectors;


@SpringBootApplication
public class SmartParkingModulithApplication {
    private final Logger log  = LoggerFactory.getLogger(SmartParkingModulithApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SmartParkingModulithApplication.class, args);
    }

    // Power up on reboot
    @Bean
    public CommandLineRunner commandLineRunner(ObjectMapper objectMapper,
                                               KafkaTemplate<String,String> kafkaTemplate,
                                               @Value("${parking.assign.slot.topic}") String topicName){
        return  args -> {
            List<Blocks> blocks = new ArrayList<>(List.of(Blocks.BLOCKA,Blocks.BLOCKB,Blocks.BLOCKC));
            List<Slots> slots = new ArrayList<>(List.of(Slots.SLOTA,Slots.SLOTB,Slots.SLOTC,Slots.SLOTD));

            EnumMap<Blocks,List<Slots>> blockSlotsMap = new EnumMap<>(Blocks.class);
            Arrays.stream(Blocks.values())
                    .forEach(block -> blockSlotsMap.put(block,Arrays.asList(Slots.values())));

            Map<String, List<String>> jsonFriendly = blockSlotsMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().name(),
                            entry -> entry.getValue().stream()
                                    .map(Slots::name)
                                    .collect(Collectors.toList())
                    ));
            try {
                String json = objectMapper.writeValueAsString(jsonFriendly);
                kafkaTemplate.send(topicName,json); // publish to a topic
            }catch (JsonProcessingException ex){
                log.error(" An error occurred: {}, and cause: {}", ex.getMessage(), ex.getCause());
            }

        };
    }
}

