package com.acabouomony.solutis.performanceandscalability.monitoring;

import com.acabouomony.solutis.performanceandscalability.messaging.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceMonitor {

    private static final double CPU_THRESHOLD = 0.75;  // 75% de uso da CPU
    private static final double MEMORY_THRESHOLD = 0.8;  // 80% de uso da Memória

    private final RabbitMQSender rabbitMQSender;
    private final RestTemplate restTemplate;

    @Autowired
    public ResourceMonitor(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 10000) // Verifica a cada 10 segundos
    public void monitorResources() {
        double cpuUsage = getMetricValue("system.cpu.usage");
        double memoryUsage = getMetricValue("jvm.memory.used") / getMetricValue("jvm.memory.max");

        if (cpuUsage > CPU_THRESHOLD) {
            rabbitMQSender.sendPerformanceScalabilityEvent("High CPU usage detected: " + (cpuUsage * 100) + "%");
        }

        if (memoryUsage > MEMORY_THRESHOLD) {
            rabbitMQSender.sendPerformanceScalabilityEvent("High Memory usage detected: " + (memoryUsage * 100) + "%");
        }
    }

    private double getMetricValue(String metricName) {
        String url = "http://localhost:8081/actuator/metrics/" + metricName;
        var response = restTemplate.getForObject(url, MetricResponse.class);
        if (response != null && response.getMeasurements().size() > 0) {
            return response.getMeasurements().get(0).getValue();
        }
        return 0.0;
    }
}