package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.model.Bitcoin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class BitcoinService {
    private static final Logger logger = LoggerFactory.getLogger(Bitcoin.class);
    public static final String TOPIC = "edmidc-bitcoin-cdc";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Bitcoin bitcoin) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Random r = new Random();
        bitcoin.setPrice(r.nextDouble());
        String jsonString = objectMapper.writeValueAsString(bitcoin);
        logger.info(String.format("#### -> Producing message -> %s", jsonString));
        this.kafkaTemplate.send(TOPIC, jsonString);
    }
}
