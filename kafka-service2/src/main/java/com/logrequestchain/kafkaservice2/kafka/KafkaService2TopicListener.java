package com.logrequestchain.kafkaservice2.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class KafkaService2TopicListener {

   private static Logger logger = LoggerFactory.getLogger(KafkaService2TopicListener.class);

    @Value("${topic.name.consumer")
    private String topicName;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload) {
        logger.info("Inside consume method for KafkaService1");
        Tracer tracer = GlobalOpenTelemetry.getTracerProvider().get("kafka-service2");
        Span parentSpan = Span.current();
        logger.debug(parentSpan.getSpanContext().getTraceId());
        logger.debug(parentSpan.getSpanContext().getSpanId());


        Span childSpan = tracer.spanBuilder("kafka-service2-topic-consumer").setParent(Context.current().with(parentSpan)).setSpanKind(SpanKind.SERVER).startSpan();
        logger.debug(childSpan.getSpanContext().getTraceId());
        logger.debug(childSpan.getSpanContext().getSpanId());
        // Make the span the current span
        try (Scope scope = childSpan.makeCurrent()) {
            String arr[] = payload.value().split(",");
            logger.info("Received request for conversion in kafka-service2 " + arr[0] + " to " + arr[1]);
        } finally {
            childSpan.end();
            parentSpan.end();
        }

    }
}