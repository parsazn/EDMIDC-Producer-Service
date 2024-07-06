package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.model.Bitcoin;
import com.edmidcbitcoincollectionsource.model.ErrorStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Random;


@Service
public class BitcoinService {
    private static final Logger logger = LoggerFactory.getLogger(Bitcoin.class);
    public static final String TOPIC = "edmidc-bitcoin-cdc";
    public static String ERROR_TOPIC = "error-message-cdc";
    private final String url = "https://api.coinlore.net/api/ticker/?id=90";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private RestTemplate restTemplate;

    public void handleFrequentGetRequest() {
        String jsonString = performGetRequest(url);
        logger.info(String.format("#### -> Producing message -> %s", jsonString));
        if(jsonString!=null) this.kafkaTemplate.send(TOPIC, jsonString);
    }

    private String performGetRequest(String url){
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            createErrorResponse(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            createErrorResponse(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            createErrorResponse(503, "Service Unavailable: " + e.getMessage());
        } catch (RestClientException e) {
            createErrorResponse(500, "Internal Server Error: " + e.getMessage());
        } catch (Exception e) {
            createErrorResponse(500, "General issue " + e.getMessage());
        }
        return null;
    }

    private void createErrorResponse(int status, String message) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(new ErrorStatus(status, message, TOPIC));
        } catch (Exception e) {
            json = "{\"status\":" + status + ",\"message\":\"" + message + ",\"topic\":\""+ TOPIC + "\"}";
        }
        this.kafkaTemplate.send(ERROR_TOPIC, json);
    }

    public void sendMessage(Bitcoin bitcoin) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Random r = new Random();
        bitcoin.setPrice(r.nextDouble());
        String jsonString = objectMapper.writeValueAsString(bitcoin);
        logger.info(String.format("#### -> Producing message -> %s", jsonString));
        this.kafkaTemplate.send(TOPIC, jsonString);
    }
}
