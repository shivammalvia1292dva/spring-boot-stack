package com.api.logger.kafkaservice1.kafka;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

@Service
public class KafkaService1TopicListener {

	private static Logger logger = LoggerFactory.getLogger(KafkaService1TopicListener.class);
    @Autowired
    private KafkaService2TopicProducer kafkaService2TopicProducer;

    @Value("${topic.name.consumer")
    private String topicName;

    private List<String> list = new ArrayList<>();

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        logger.info("Inside consume method for KafkaService1");
        Tracer tracer = GlobalOpenTelemetry.getTracerProvider().get("kafka-service1");
        Span parentSpan = Span.current();
        logger.debug(parentSpan.getSpanContext().getTraceId());
        logger.debug(parentSpan.getSpanContext().getSpanId());

        Span childSpan = tracer.spanBuilder("kafka-service1-consumer").setParent(Context.current().with(parentSpan)).setSpanKind(SpanKind.SERVER)
                .startSpan();
        logger.debug(childSpan.getSpanContext().getTraceId());
        logger.debug(childSpan.getSpanContext().getSpanId());
        try (Scope scope = childSpan.makeCurrent()) {
            logger.debug(payload.value());
            logger.debug(payload.headers().toString());
            payload.headers().forEach(header -> System.out
                    .println("header key = " + header.key() + " value = " + header.value().toString()));
            String arr[] = payload.value().split(",");
            list.add("Received request for conversion of " + arr[0] + " to " + arr[1]);
            kafkaService2TopicProducer.send(payload.value());
        } finally {
            childSpan.end();
            parentSpan.end();
        }
        logger.info("Consume method call finished");
    }

    public List<String> getList() {
        return list;
    }
}