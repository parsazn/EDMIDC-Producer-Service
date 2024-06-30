package com.edmidcbitcoincollectionsource.config;

import com.edmidcbitcoincollectionsource.service.HeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private HeartbeatService service;

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void execute() {
        service.sendHeartbeatSignal();
    }
}
