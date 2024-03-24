package com.edmidcbitcoincollectionsource.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.edmidcbitcoincollectionsource.services.BitcoinService.TOPIC;


@RestController
public class controllerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/")
    public String index() {
        kafkaTemplate.send(TOPIC, "msg");
        return "Greetings from Spring Boot!";
    }
}
