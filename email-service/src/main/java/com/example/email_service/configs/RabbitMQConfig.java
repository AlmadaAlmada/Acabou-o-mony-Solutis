package com.example.email_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.email.name.confirmation}")
    private String queueConfirmation;
    @Value("${broker.queue.email.name.authentication}")
    private String queueCode2FA;

    @Bean
    public Queue queueConfirmation() {
        return new Queue(queueConfirmation, true);
    }

    @Bean
    public Queue queueCode2FA() {
        return new Queue(queueCode2FA, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
