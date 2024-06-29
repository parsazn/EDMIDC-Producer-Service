package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.model.Heartbeat;
import com.edmidcbitcoincollectionsource.repository.HeartbeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HeartbeatService {

    private HeartbeatRepository repository;

    public void sendHearbeatSignal(){
        Optional<Heartbeat> heartbeatOptional = repository.findById(1L);
        if (heartbeatOptional.isEmpty()) throw new RuntimeException("Bam");
        Heartbeat heartbeat = heartbeatOptional.get();
        heartbeat.setLastPulse(new Timestamp(System.currentTimeMillis()));
        repository.save(heartbeat);
    }
}
