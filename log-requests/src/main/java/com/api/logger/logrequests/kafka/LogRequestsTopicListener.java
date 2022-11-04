package com.api.logger.logrequests.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogRequestsTopicListener {

    Logger logger = LoggerFactory.getLogger(LogRequestsTopicListener.class);
    @Value("${topic.name.consumer")
    private String topicName;

    @Autowired
    private LogRequestsTopicProducer logRequestsTopicProducer;

    private List<String> list = new ArrayList<>();

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")

    public void consume(ConsumerRecord<String, String> payload,@Header("x-amzn-trace-id") String small,@Header("X-Amzn-Trace-Id") String capital) {
        payload.headers();
        logger.info("consume method called");
        logger.info(payload.value());
        String arr[] = payload.value().split(",");
        list.add("Received request for conversion of " + arr[0] + " to " + arr[1]);
        logRequestsTopicProducer.send(payload.value());
    }

    public List<String> getList() {
        return list;
    }
}