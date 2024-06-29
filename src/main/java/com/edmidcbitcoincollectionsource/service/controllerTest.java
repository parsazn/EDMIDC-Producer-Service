package com.edmidcbitcoincollectionsource.service;

import com.edmidcbitcoincollectionsource.model.Bitcoin;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class controllerTest {

    private BitcoinService service;

    @GetMapping("/")
    public String index() throws JsonProcessingException {
        service.sendMessage(Bitcoin.builder().name("test").build());
        return "Message sent";
    }
}
