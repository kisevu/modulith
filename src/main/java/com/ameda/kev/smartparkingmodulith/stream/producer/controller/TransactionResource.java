package com.ameda.kev.smartparkingmodulith.stream.producer.controller;

import com.ameda.kev.smartparkingmodulith.stream.producer.domain.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionResource {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    public TransactionResource(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public String sendTransaction() throws Exception{
        for ( int i = 0; i < 50; i++){
            String transactionIds  = "txn-" + System.currentTimeMillis() + "-"+i;
            double amount =  8000 + new Random().nextDouble() * (11000 - 8000);
            Transaction txn = new Transaction(UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    amount,
                    Instant.now());
            try{
                String convertedJson= objectMapper.writeValueAsString(txn);
                log.info(" json sent to kafka topic:{}",convertedJson);
                kafkaTemplate.send("transactions",convertedJson);
                log.info(" notification sent to kafka topic");
                return "successfully sent data to transactions topic";
            }catch (JsonProcessingException ex){
                log.error("Error occurred :{} and cause: {}",ex.getMessage(),ex.getCause());
                return "Unable to send data to kafka topic";
            }
        }
        return "result on preceding logs top";
    }
}
