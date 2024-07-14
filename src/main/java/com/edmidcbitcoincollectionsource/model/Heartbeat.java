package com.edmidcbitcoincollectionsource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "heartbeat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Heartbeat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "heartbeat_seq")
    @SequenceGenerator(name = "heartbeat_seq", sequenceName = "heartbeat_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "service")
    private String service;

    @Column(name = "status")
    private String status;

    @Column(name = "last_pulse")
    private Timestamp lastPulse;

}
