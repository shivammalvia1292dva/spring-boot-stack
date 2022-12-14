package com.in28minutes.microservices.currencyconversionservice.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CurencyExchangeLogTopicProducer {

    Logger logger = LoggerFactory.getLogger(CurencyExchangeLogTopicProducer.class);
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        ProducerRecord<String,String> producer = new ProducerRecord<>(topicName,message);
        producer.headers().add("temp","super".getBytes());
        logger.info("Payload message sent to : {}" + message);
        producer.headers();
        kafkaTemplate.send(producer);

    }

}