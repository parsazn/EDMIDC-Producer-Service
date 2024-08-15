package com.edmidcproducerservicesource.repository;

import com.edmidcproducerservicesource.model.Heartbeat;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HeartbeatRepository extends CrudRepository<Heartbeat, Long> {
    Optional<Heartbeat> findAllByServiceEqualsIgnoreCase(String service);
}

