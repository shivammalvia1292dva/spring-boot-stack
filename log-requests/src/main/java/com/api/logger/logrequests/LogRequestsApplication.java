package com.api.logger.logrequests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class LogRequestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogRequestsApplication.class, args);
	}
	
}
