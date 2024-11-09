package com.acabouomony.solutis.performanceandscalability.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMQConfig {

    public static final String PERFORMANCE_SCALABILITY_QUEUE = "performance.scalability.queue";

        @Bean
    public Queue performanceScalabilityQueue() {
        return new Queue(PERFORMANCE_SCALABILITY_QUEUE, true);
    }
}