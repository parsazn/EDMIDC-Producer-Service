package com.edmidcproducerservicesource.controller;

import com.edmidcproducerservicesource.service.KafkaFailedMessagesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EndpointController {

    private KafkaFailedMessagesService kafkaFailedMessagesService;

    @PostMapping("/resend/failed-messages")
    public String resendFailedMessages(@RequestBody int size) {
        int totalMessages = kafkaFailedMessagesService.resendFailedMessagesWithinABatch(size);
        return totalMessages + " messages have been resent";
    }
}

