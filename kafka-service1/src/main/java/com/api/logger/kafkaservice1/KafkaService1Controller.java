package com.api.logger.kafkaservice1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.logger.kafkaservice1.kafka.KafkaService1TopicListener;

import io.micrometer.core.annotation.Timed;

@RestController
public class KafkaService1Controller {

    Logger logger = LoggerFactory.getLogger(KafkaService1Controller.class);
//    private MeterRegistry meterRegistry;
//
//    public LogRequestsController(MeterRegistry meterRegistry) {
//        this.meterRegistry = meterRegistry;
//    }

    @Autowired
    private KafkaService1TopicListener logRequestsTopicListener;

    @Timed(value="shashi_logrequest_retrieval_time", description = "Time spent retieving the log request list")
    @GetMapping("/kafka-service1")
    public List<String> retrieveLogRequests() {
        logger.info("retrieve log request called");
        return logRequestsTopicListener.getList();
    }
}
