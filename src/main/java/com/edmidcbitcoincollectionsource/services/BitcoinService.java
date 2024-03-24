package com.edmidcbitcoincollectionsource.services;

import com.edmidcbitcoincollectionsource.models.Bitcoin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class BitcoinService {
    private static final Logger logger = LoggerFactory.getLogger(Bitcoin.class);

    public static final String TOPIC = "edmidc-bitcoin-cdc";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}
