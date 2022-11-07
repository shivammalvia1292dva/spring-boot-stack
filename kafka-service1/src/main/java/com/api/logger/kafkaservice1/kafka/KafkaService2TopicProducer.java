package com.api.logger.kafkaservice1.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService2TopicProducer {

    Logger logger = LoggerFactory.getLogger(KafkaService2TopicProducer.class);
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        logger.info("Payload message sent to topic " + topicName + "with values : " + message);
        kafkaTemplate.send(topicName, message);
    }

}