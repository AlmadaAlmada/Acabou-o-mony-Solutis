package com.acabouomony.solutis.performanceandscalability.service;

import com.acabouomony.solutis.performanceandscalability.monitoring.MetricResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MetricsService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void fetchMetrics() {
        // URLs dos serviços monitorados
        String[] services = {
                "http://localhost:8082/actuator/metrics",
                "http://localhost:8083/actuator/metrics"
        };

        for (String serviceUrl : services) {
            try {
                // Monitorar tempo médio de resposta
                double responseTime = getMetricValue(serviceUrl + "/http.server.requests", "TOTAL_TIME") /
                        getMetricValue(serviceUrl + "/http.server.requests", "COUNT");
                System.out.println("Tempo médio de resposta: " + responseTime + "s");

                // Monitorar uso de CPU
                double cpuUsage = getMetricValue(serviceUrl + "/system.cpu.usage", "value");
                System.out.println("Uso de CPU: " + (cpuUsage * 100) + "%");

                // Monitorar uso de memória
                double memoryUsed = getMetricValue(serviceUrl + "/jvm.memory.used", "value");
                double memoryMax = getMetricValue(serviceUrl + "/jvm.memory.max", "value");
                double memoryUsage = (memoryUsed / memoryMax) * 100;
                System.out.println("Uso de memória: " + memoryUsage + "%");
            } catch (Exception e) {
                System.err.println("Erro ao buscar métricas para o serviço em: " + serviceUrl + " - " + e.getMessage());
            }
        }
    }

    private double getMetricValue(String url, String statistic) {
        try {
            var response = restTemplate.getForObject(url, MetricResponse.class);
            if (response != null && response.getMeasurements() != null) {
                return response.getMeasurements().stream()
                        .filter(m -> m.getStatistic().equals(statistic))
                        .mapToDouble(MetricResponse.Measurement::getValue)
                        .findFirst()
                        .orElse(0.0);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar valor da métrica: " + e.getMessage());
        }
        return 0.0;
    }
}