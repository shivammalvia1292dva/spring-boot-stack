package com.logrequestchain.logrequestchain.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogRequestsTopicListener {

    Logger logger = LoggerFactory.getLogger(LogRequestsTopicListener.class);
    @Value("${topic.name.consumer")
    private String topicName;

    private List<String> list = new ArrayList<>();

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        logger.info("log-request-chain-topic-consumer-called");
        logger.info(payload.value());
        String arr[] = payload.value().split(",");
        list.add("Received request for conversion of  log-request consumer" + arr[0] + " to " + arr[1]);
    }

    public List<String> getList() {
        return list;
    }
}