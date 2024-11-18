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
    private static final double RESPONSE_TIME_THRESHOLD = 1.0;  // 1 segundo de tempo de resposta

    private final RabbitMQSender rabbitMQSender;
    private final RestTemplate restTemplate;

    @Autowired
    public ResourceMonitor(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 1000) // Verifica a cada 1 segundo
    public void monitorResources() {
        double responseTime = getAverageResponseTime();

        if (responseTime > RESPONSE_TIME_THRESHOLD) {
            rabbitMQSender.sendPerformanceScalabilityEvent("High response time detected: " + responseTime + "s");
        }

        // Monitorar uso de CPU e Memória (mantém lógica anterior)
        double cpuUsage = getMetricValue("system.cpu.usage");
        if (cpuUsage > CPU_THRESHOLD) {
            rabbitMQSender.sendPerformanceScalabilityEvent("High CPU usage detected: " + (cpuUsage * 100) + "%");
        }

        double memoryUsage = getMetricValue("jvm.memory.used") / getMetricValue("jvm.memory.max");
        if (memoryUsage > MEMORY_THRESHOLD) {
            rabbitMQSender.sendPerformanceScalabilityEvent("High Memory usage detected: " + (memoryUsage * 100) + "%");
        }
    }

    private double getAverageResponseTime() {
        String url = "http://localhost:8081/actuator/metrics/http.server.requests";
        var response = restTemplate.getForObject(url, MetricResponse.class);

        if (response != null && response.getMeasurements().size() > 0) {
            double totalTime = response.getMeasurements().stream()
                    .filter(m -> "TOTAL_TIME".equals(m.getStatistic()))
                    .mapToDouble(MetricResponse.Measurement::getValue)
                    .findFirst()
                    .orElse(0.0);

            double count = response.getMeasurements().stream()
                    .filter(m -> "COUNT".equals(m.getStatistic()))
                    .mapToDouble(MetricResponse.Measurement::getValue)
                    .findFirst()
                    .orElse(1.0); // Evitar divisão por zero

            return count > 0 ? totalTime / count : 0.0; // Cálculo do tempo médio
        }

        return 0.0;
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
