package com.edmidcproducerservicesource.service;

import com.edmidcproducerservicesource.config.KafkaProducerConfig;
import com.edmidcproducerservicesource.config.ProducerMetadataConfig;
import com.edmidcproducerservicesource.model.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Date;


@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    public final String ERROR_TOPIC;
    private final String URL;
    private final String MEASUREMENT_NAME;
    private final String TAG;
    private final String TOPIC;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;

    private final KafkaFailedMessagesService kafkaFailedMessagesService;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate, KafkaProducerConfig kafkaProducerConfig, ProducerMetadataConfig producerMetadataConfig, KafkaFailedMessagesService kafkaFailedMessagesService) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.ERROR_TOPIC = kafkaProducerConfig.getErrorKafkaTopic();
        this.TOPIC = kafkaProducerConfig.getKafkaTopic();
        this.URL = producerMetadataConfig.getUrl();
        this.MEASUREMENT_NAME = producerMetadataConfig.getMeasurementName();
        this.TAG = producerMetadataConfig.getTag();
        this.kafkaFailedMessagesService = kafkaFailedMessagesService;
    }

    public void handleFrequentGetRequest() {
        try {
            String jsonString = performGetRequest(URL);

            if (jsonString == null || jsonString.isEmpty()) {
                logger.warn("Received empty response from URL: {}", URL);
                return;
            }

            logger.info("#### -> Producing message -> {}", jsonString);

            // Manually construct the final JSON string
            String updatedJsonString = String.format(
                    "{\"measurementName\":\"%s\",\"tag\":\"%s\",\"created\":\"%s\",\"result\":%s}",
                    MEASUREMENT_NAME, TAG, new Date().getTime(), jsonString
            );

            sendKafkaMessage(TOPIC, updatedJsonString);
        } catch (Exception e) {
            logger.error("Error handling frequent GET request", e);
        }
    }


    private String performGetRequest(String url) {
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
            json = "{\"status\":" + status + ",\"message\":\"" + message.replaceAll("\"", "") + "\"" + ",\"topic\":\"" + TOPIC + "\"}";
        }

        String updatedJsonString = String.format(
                "{\"measurementName\":\"%s\",\"tag\":\"%s\",\"result\":%s}",
                "Error", "error", json
        );

        sendKafkaMessage(ERROR_TOPIC, updatedJsonString);
    }

    public void sendKafkaMessage(String topicName, String kafkaMessage) {
        try {
            this.kafkaTemplate.send(topicName, kafkaMessage);
        } catch (Exception exception) {
            kafkaFailedMessagesService.createFailedTask(kafkaMessage, exception.getMessage(), topicName);
        }
    }
}
