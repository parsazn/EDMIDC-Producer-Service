package com.edmidcproducerservicesource.service;

import com.edmidcproducerservicesource.config.ProducerMetadataConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final HeartbeatService heartbeatService;
    private final ProducerService producerService;
    private final ProducerMetadataConfig producerMetadataConfig;
    private final KafkaFailedMessagesService kafkaFailedMessagesService;

    public ScheduledTasks(HeartbeatService heartbeatService, ProducerService producerService, ProducerMetadataConfig producerMetadataConfig, KafkaFailedMessagesService kafkaFailedMessagesService) {
        this.heartbeatService = heartbeatService;
        this.producerService = producerService;
        this.producerMetadataConfig = producerMetadataConfig;
        this.kafkaFailedMessagesService = kafkaFailedMessagesService;
    }

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void execute() {
        heartbeatService.sendHeartbeatSignal();
    }

    @Scheduled(cron = "#{@producerMetadataConfig.cronFrequency}") // Cron expression for running every minute
    public void executeGetRequest() {
        producerService.handleFrequentGetRequest();
    }

    @Scheduled(cron = "0 0 * * * *") // Cron expression for running every hour
    public void resendFailedMessages() {
        kafkaFailedMessagesService.resendFailedMessagesWithinABatch(30);
    }
}
