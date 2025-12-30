package com.ameda.kev.smartparkingmodulith.stream.stream.config;

import com.ameda.kev.smartparkingmodulith.stream.producer.domain.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

/**
 * Author: kev.Ameda
 */
@Configuration
@EnableKafkaStreams
public class FraudDetectionStream {

    private final Logger log = LoggerFactory.getLogger(FraudDetectionStream.class);

    private final ObjectMapper mapper;

    public FraudDetectionStream(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public KStream<String,String> fraudDetectStream(StreamsBuilder streamsBuilder){
        //read message from source topic
        KStream<String, String> transactionsStream = streamsBuilder.stream("transactions");

        //filter out transactions if exceeds 10k
        KStream<String, String> fraudTransactionStream = transactionsStream.filter((key, value) -> isSuspicious(value))
                .peek((key, value) -> {
                    log.info("FRAUD ALERT - transactionId={}, value={}", key, value);
                });

        //write to some destination-topic
        fraudTransactionStream.to("fraud-alerts");
        return transactionsStream;

    }

    private boolean isSuspicious(String value ){
        try{
            Transaction txn = mapper.readValue(value, Transaction.class);
            return  txn.amount() > 10000;
        }catch (Exception exception){
            return false;
        }
    }
}
