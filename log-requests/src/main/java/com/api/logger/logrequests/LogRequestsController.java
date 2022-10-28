package com.api.logger.logrequests;

import com.api.logger.logrequests.kafka.LogRequestsTopicListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogRequestsController {

    Logger logger = LoggerFactory.getLogger(LogRequestsController.class);

    @Autowired
    private LogRequestsTopicListener logRequestsTopicListener;


    @GetMapping("/log-requests")
    public List<String> retrieveLogRequests() {
        logger.info("retrieve log request called");
        return logRequestsTopicListener.getList();
    }
}
