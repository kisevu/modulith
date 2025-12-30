package com.ameda.kev.smartparkingmodulith.stream.producer.domain;


import com.ameda.kev.smartparkingmodulith.entry.vo.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;


/**
 * Author: kev.Ameda
 */
@Builder
public record Transaction(String transactionId,
                          String userId,
                          Double amount,
                          Instant transactionTime) {

    public Transaction {
        Assert.notNull("transactionId",transactionId);
        Assert.notNull("userId",userId);
        Assert.notNull("amount",amount);
        Assert.notNull("transactionTime",transactionTime);
    }


}

