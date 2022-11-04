package com.api.logger.logrequests.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogRequestsTopicProducer {

    Logger logger = LoggerFactory.getLogger(LogRequestsTopicProducer.class);
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        logger.info("Payload message sent to : {}" + message);
        kafkaTemplate.send(topicName, message);
    }

}