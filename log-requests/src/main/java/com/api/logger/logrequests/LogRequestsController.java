package com.api.logger.logrequests;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.logger.logrequests.kafka.LogRequestsTopicListener;

import io.micrometer.core.annotation.Timed;

@RestController
public class LogRequestsController {

    Logger logger = LoggerFactory.getLogger(LogRequestsController.class);
//    private MeterRegistry meterRegistry;
//
//    public LogRequestsController(MeterRegistry meterRegistry) {
//        this.meterRegistry = meterRegistry;
//    }

    @Autowired
    private LogRequestsTopicListener logRequestsTopicListener;

    @Timed(value="shashi_logrequest_retrieval_time", description = "Time spent retieving the log request list")
    @GetMapping("/log-requests")
    public List<String> retrieveLogRequests() {
        logger.info("retrieve log request called");
        return logRequestsTopicListener.getList();
    }
}
