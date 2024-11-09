package com.acabouomony.solutis.performanceandscalability.messaging;

import com.acabouomony.solutis.performanceandscalability.config.RabbitMQConfig;
import com.acabouomony.solutis.performanceandscalability.service.CacheManagerService;
import com.acabouomony.solutis.performanceandscalability.service.ScalingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQReceiver {

    private final ScalingService scalingService;

    public RabbitMQReceiver(ScalingService scalingService) {
        this.scalingService = scalingService;
    }

    @RabbitListener(queues = RabbitMQConfig.PERFORMANCE_SCALABILITY_QUEUE)
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida da fila: " + message);
        scalingService.adjustScalingAndPerformance(message);
    }
}