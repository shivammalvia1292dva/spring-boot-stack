package com.api.logger.logrequests.kafka;

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
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

@Service
public class LogRequestsTopicListener {

	@Autowired
	private LogRequestsTopicProducer logRequestsTopicProducer;

	Logger logger = LoggerFactory.getLogger(LogRequestsTopicListener.class);
	@Value("${topic.name.consumer")
	private String topicName;

	private List<String> list = new ArrayList<>();

	@KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
	public void consume(ConsumerRecord<String, String> payload) {

		Tracer tracer = GlobalOpenTelemetry.getTracerProvider().get("log-requests");
		Span parentSpan = Span.current();
		logger.info("=====================================================================================");
		logger.info("=====================================================================================");
		logger.info(parentSpan.getSpanContext().getTraceId());
		logger.info(parentSpan.getSpanContext().getSpanId());
		logger.info("" + parentSpan.getSpanContext().isRemote());
		logger.info("" + parentSpan.getSpanContext().isSampled());
		logger.info("" + parentSpan.getSpanContext().isValid());

		Span childSpan = tracer.spanBuilder("log-requests_consume_span").setParent(Context.current().with(parentSpan))
				.startSpan();
		logger.info("=====================================================================================");
		logger.info("=====================================================================================");
		logger.info(childSpan.getSpanContext().getTraceId());
		logger.info(childSpan.getSpanContext().getSpanId());
		logger.info("" + childSpan.getSpanContext().isRemote());
		logger.info("" + childSpan.getSpanContext().isSampled());
		logger.info("" + childSpan.getSpanContext().isValid());
		try (Scope scope = childSpan.makeCurrent()) {
			logger.info(payload.value());
			logger.info(payload.headers().toString());
			payload.headers().forEach(header -> System.out
					.println("header key = " + header.key() + " value = " + header.value().toString()));
			logger.info("consume method called");
			String arr[] = payload.value().split(",");
			list.add("Received request for conversion of " + arr[0] + " to " + arr[1]);
			logRequestsTopicProducer.send(payload.value());
			scope.close();
		} finally {
			childSpan.end();
			parentSpan.end();
		}
	}

	public List<String> getList() {
		return list;
	}
}