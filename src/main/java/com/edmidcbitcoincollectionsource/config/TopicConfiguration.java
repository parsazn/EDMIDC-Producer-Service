package com.edmidcbitcoincollectionsource.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class TopicConfiguration {

    @Bean
    public KafkaAdmin admin()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                "${spring.kafka.producer.bootstrap-servers}");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1()
    {
        return TopicBuilder.name("${spring.kafka.topic}")
                .partitions(1)
                .replicas(2)
                .build();
    }
}
