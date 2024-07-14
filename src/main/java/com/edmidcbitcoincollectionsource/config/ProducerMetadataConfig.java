package com.edmidcbitcoincollectionsource.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
public class ProducerMetadataConfig {
    @Value("${spring.resource.url}")
    private String url;
    @Value("${spring.resource.measurementName}")
    private String measurementName;
    @Value("${spring.resource.tag}")
    private String tag;
    @Value("${spring.resource.frequency}")
    private String cronFrequency;
    @Value("spring.application.name")
    private String applicationName;
}
