package com.edmidcbitcoincollectionsource.services;

import com.edmidcbitcoincollectionsource.models.Bitcoin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class controllerTest {

    private BitcoinService service;

    @GetMapping("/")
    public String index() {
        service.sendMessage(Bitcoin.builder().name("test").build());
        return "Message sent";
    }
}
