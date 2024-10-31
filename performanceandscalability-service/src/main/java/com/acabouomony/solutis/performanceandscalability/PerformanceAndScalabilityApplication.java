package com.acabouomony.solutis.performanceandscalability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {
        "com.acabouomony.solutis.performanceandscalability.controller",
        "com.acabouomony.solutis.performanceandscalability.service"
})
@EnableCaching
public class PerformanceAndScalabilityApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerformanceAndScalabilityApplication.class, args);
    }
}