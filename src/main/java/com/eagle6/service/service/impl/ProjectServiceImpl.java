package com.eagle6.service.service.impl;

import com.eagle6.service.kafka.channel.ConsumerChannel;
import com.eagle6.service.kafka.model.Greeting;
import com.eagle6.service.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @StreamListener(ConsumerChannel.CHANNEL)
    public void consume(Greeting greeting) {
        log.info("Received message: {}.", greeting.getMessage());
    }
}
