package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.config.ProducerMetadataConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final HeartbeatService heartbeatService;
    private final ProducerService producerService;
    private final ProducerMetadataConfig producerMetadataConfig;
    public ScheduledTasks(HeartbeatService heartbeatService, ProducerService producerService, ProducerMetadataConfig producerMetadataConfig) {
        this.heartbeatService = heartbeatService;
        this.producerService = producerService;
        this.producerMetadataConfig = producerMetadataConfig;
    }

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void execute() {
        heartbeatService.sendHeartbeatSignal();
    }

    @Scheduled(cron = "#{@producerMetadataConfig.cronFrequency}") // Cron expression for running every minute
    public void executeGetRequest() {
        producerService.handleFrequentGetRequest();
    }
}
