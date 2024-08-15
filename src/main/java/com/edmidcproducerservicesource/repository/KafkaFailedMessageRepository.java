package com.edmidcproducerservicesource.repository;

import com.edmidcproducerservicesource.model.KafkaFailedMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface KafkaFailedMessageRepository extends CrudRepository<KafkaFailedMessage, Long> {
    @Query("""
        select k from KafkaFailedMessage k order by k.retries asc , k.createdDate desc
    """)
    Set<KafkaFailedMessage> findAllMessagesOrderByRetriesDesc(PageRequest pageRequest);
}
