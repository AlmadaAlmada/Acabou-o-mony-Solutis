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

        if (message.contains("High response time")) {
            System.out.println("Evento 'High response time detected' identificado. Ajustando o balanceamento de carga...");
            scalingService.handleHighResponseTime();
        } else if (message.contains("High CPU")) {
            System.out.println("Evento 'High CPU usage detected' identificado. Ajustando réplicas...");
            scalingService.scaleUpReplicas();
        } else if (message.contains("High Memory")) {
            System.out.println("Evento 'High Memory usage detected' identificado. Ajustando configurações de cache...");
            scalingService.adjustCacheSettings();
        } else {
            System.out.println("Evento não reconhecido. Nenhuma ação realizada.");
        }
    }
}