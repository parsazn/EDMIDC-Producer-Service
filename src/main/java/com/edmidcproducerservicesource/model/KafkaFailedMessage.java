package com.edmidcproducerservicesource.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "SpringApplicationName")
@Getter
@Setter
public class KafkaFailedMessage {

    public static final String TABLE_NAME= "ARTICLES";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String messageContent;

    @Column(columnDefinition = "text")
    private String errorMessage;

    private Date createdDate = new Date();

    private Date updatedDate;

    private String topicName;

    private long retries = 0;
}
