package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.model.Heartbeat;
import com.edmidcbitcoincollectionsource.repository.HeartbeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class HeartbeatService {

    private HeartbeatRepository repository;

    public void sendHeartbeatSignal() {
        Heartbeat heartbeat = repository.findById(1L)
                .orElseGet(() -> {
                    Heartbeat newHeartbeat = new Heartbeat();
                    newHeartbeat.setId(1L);
                    newHeartbeat.setService("Papi");
                    newHeartbeat.setStatus("Working");
                    return newHeartbeat;
                });

        heartbeat.setLastPulse(getCurrentTimestamp());
        repository.save(heartbeat);
    }

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
