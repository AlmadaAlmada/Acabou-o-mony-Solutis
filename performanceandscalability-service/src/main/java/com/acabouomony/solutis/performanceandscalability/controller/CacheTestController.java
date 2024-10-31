package com.acabouomony.solutis.performanceandscalability.controller;

import com.acabouomony.solutis.performanceandscalability.service.CacheTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheTestController {

    private final CacheTestService cacheTestService;

    public CacheTestController(CacheTestService cacheTestService) {
        this.cacheTestService = cacheTestService;
    }

    @GetMapping("/test-cache")
    public String testCache(@RequestParam String param) {
        return cacheTestService.getExampleData(param);
    }
}