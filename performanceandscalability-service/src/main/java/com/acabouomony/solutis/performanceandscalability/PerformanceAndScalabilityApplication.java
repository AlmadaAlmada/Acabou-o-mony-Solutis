package com.acabouomony.solutis.performanceandscalability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.acabouomony.solutis.performanceandscalability")
@EnableCaching
@EnableScheduling
public class PerformanceAndScalabilityApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerformanceAndScalabilityApplication.class, args);
    }
}