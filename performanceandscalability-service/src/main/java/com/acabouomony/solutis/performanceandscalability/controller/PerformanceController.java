package com.acabouomony.solutis.performanceandscalability.controller;

import com.acabouomony.solutis.performanceandscalability.messaging.RabbitMQSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PerformanceController {

    private final RabbitMQSender rabbitMQSender;

    public PerformanceController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/sendEvent")
    public String sendEvent(@RequestParam String eventMessage) {
        rabbitMQSender.sendPerformanceScalabilityEvent(eventMessage);
        return "Evento enviado: " + eventMessage;
    }
}