package com.solutis.acabouomony.security.rabbit.producer;

import com.solutis.acabouomony.account.dto.response.EmailDTO;
import com.solutis.acabouomony.security.model.MultiFactorAuth;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name.authentication}")
    private String routingKey;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    public SecurityProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessageEmail(MultiFactorAuth mfa) {
        EmailDTO emailDto = new EmailDTO(mfa.getUserId(), mfa.getEmail(), mfa.getCode());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, emailDto);
    }
}