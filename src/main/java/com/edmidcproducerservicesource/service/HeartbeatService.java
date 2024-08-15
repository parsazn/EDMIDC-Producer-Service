package com.edmidcproducerservicesource.service;

import com.edmidcproducerservicesource.config.ProducerMetadataConfig;
import com.edmidcproducerservicesource.model.Heartbeat;
import com.edmidcproducerservicesource.repository.HeartbeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class HeartbeatService {
    private HeartbeatRepository repository;
    private ProducerMetadataConfig producerMetadataConfig;

    public void sendHeartbeatSignal() {
        Heartbeat heartbeat = repository.findAllByServiceEqualsIgnoreCase(producerMetadataConfig.getApplicationName())
                .orElseGet(() -> {
                    Heartbeat newHeartbeat = new Heartbeat();
                    newHeartbeat.setService(producerMetadataConfig.getApplicationName());
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
