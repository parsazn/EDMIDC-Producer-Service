package com.edmidcproducerservicesource.service;

import com.edmidcproducerservicesource.model.KafkaFailedMessage;
import com.edmidcproducerservicesource.repository.KafkaFailedMessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class KafkaFailedMessagesService {
    private KafkaFailedMessageRepository kafkaFailedMessageRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void createFailedTask(String messageContent, String errorMessage, String topicName) {
        KafkaFailedMessage kafkaFailedMessage = new KafkaFailedMessage();
        kafkaFailedMessage.setMessageContent(messageContent);
        kafkaFailedMessage.setErrorMessage(errorMessage);
        kafkaFailedMessage.setTopicName(topicName);

        kafkaFailedMessageRepository.save(kafkaFailedMessage);
    }

    @Transactional
    public int resendFailedMessagesWithinABatch(int size) {
        Set<KafkaFailedMessage> failedMessages = obtainKafkaFailedMessages(size);
        failedMessages.stream().forEach(f -> retrySendingKafkaMessage(f));
        return failedMessages.size();
    }

    public void retrySendingKafkaMessage(KafkaFailedMessage kafkaFailedMessage) {
        try {
            this.kafkaTemplate.send(kafkaFailedMessage.getTopicName(), kafkaFailedMessage.getMessageContent());
            removeSentMessagesWithSuccess(kafkaFailedMessage);
        } catch (Exception e) {
            retryFailedMessage(kafkaFailedMessage);
        }

    }

    public void removeSentMessagesWithSuccess(KafkaFailedMessage kafkaFailedMessage) {
        kafkaFailedMessageRepository.delete(kafkaFailedMessage);
    }

    public void retryFailedMessage(KafkaFailedMessage kafkaFailedMessage) {
        kafkaFailedMessage.setRetries(kafkaFailedMessage.getRetries() + 1);
        kafkaFailedMessage.setUpdatedDate(new Date());
        kafkaFailedMessageRepository.save(kafkaFailedMessage);
    }

    private Set<KafkaFailedMessage> obtainKafkaFailedMessages(int batchSize) {
        return kafkaFailedMessageRepository.findAllMessagesOrderByRetriesDesc(PageRequest.of(0, batchSize, Sort.by(Sort.Direction.DESC, "retries")));
    }
}
