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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text" , name = "message_content")
    private String messageContent;

    @Column(columnDefinition = "text", name = "error_message")
    private String errorMessage;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "topic_name")
    private String topicName;

    private long retries = 0;
}
