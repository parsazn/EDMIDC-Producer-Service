package com.edmidcbitcoincollectionsource.config;

import com.edmidcbitcoincollectionsource.service.BitcoinService;
import com.edmidcbitcoincollectionsource.service.HeartbeatService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private HeartbeatService heartbeatService;
    private BitcoinService bitcoinService;

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void execute() {
        heartbeatService.sendHeartbeatSignal();
    }

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void executeGetRequest() {
        bitcoinService.handleFrequentGetRequest();
    }
}
