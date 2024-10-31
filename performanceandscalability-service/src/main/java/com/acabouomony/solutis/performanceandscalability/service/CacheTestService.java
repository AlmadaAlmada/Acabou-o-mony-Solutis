package com.acabouomony.solutis.performanceandscalability.service;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheTestService {

    @Cacheable("exampleCache")
    public String getExampleData(String param) {
        System.out.println("Executando método getExampleData para o parâmetro: " + param);
        return "Dados processados para " + param;
    }
}