package com.edmidcbitcoincollectionsource.repository;

import com.edmidcbitcoincollectionsource.model.Heartbeat;
import org.springframework.data.repository.CrudRepository;

public interface HeartbeatRepository extends CrudRepository<Heartbeat, Long> {
}

