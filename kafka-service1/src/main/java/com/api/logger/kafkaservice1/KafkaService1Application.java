package com.api.logger.kafkaservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaService1Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaService1Application.class, args);
	}
	
}
