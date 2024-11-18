package com.acabouomony.solutis.performanceandscalability.controller;


import com.acabouomony.solutis.performanceandscalability.service.MetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/fetch-metrics")
    public String fetchMetrics() {
        metricsService.fetchMetrics();
        return "Métricas coletadas com sucesso!";
    }
}