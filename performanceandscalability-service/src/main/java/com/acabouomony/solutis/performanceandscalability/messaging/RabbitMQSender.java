package com.acabouomony.solutis.performanceandscalability.messaging;
import com.acabouomony.solutis.performanceandscalability.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPerformanceScalabilityEvent(String eventMessage) {
        System.out.println("Enviando mensagem para a fila: " + eventMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.PERFORMANCE_SCALABILITY_QUEUE, eventMessage);
    }
}