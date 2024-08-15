package com.edmidcproducerservicesource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdmidcProducerServiceSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdmidcProducerServiceSourceApplication.class, args);
	}

}
