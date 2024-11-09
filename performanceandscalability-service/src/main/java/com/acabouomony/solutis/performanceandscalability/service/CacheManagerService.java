package com.acabouomony.solutis.performanceandscalability.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;


@Service
public class CacheManagerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long HIGH_MEMORY_THRESHOLD = 400 * 1024 * 1024; // 400MB

    public CacheManagerService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void adjustCacheSettings() {
        Long usedMemory = getUsedMemory();
        if (usedMemory == null) {
            System.out.println("Não foi possível obter o uso de memória do Redis.");
            return;
        }

        System.out.println("Uso atual de memória do cache: " + usedMemory / 1024 / 1024 + " MB");

        if (usedMemory > HIGH_MEMORY_THRESHOLD) {
            System.out.println("Memória do cache alta. Ajustando as configurações para otimizar o uso de memória...");
            setCacheEvictionPolicy("allkeys-lru");
            setDefaultExpirationTime(60); // Ajusta o TTL para 60 segundos
        } else {
            System.out.println("Memória do cache dentro dos limites aceitáveis.");
            setCacheEvictionPolicy("volatile-lru");
            setDefaultExpirationTime(300); // Ajusta o TTL para 5 minutos
        }
    }

    private Long getUsedMemory() {
        try {
            return redisTemplate.execute((RedisConnection connection) -> {
                String info = connection.info("memory").getProperty("used_memory");
                return info != null ? Long.parseLong(info) : 0L;
            });
        } catch (Exception e) {
            System.err.println("Erro ao obter o uso de memória do Redis: " + e.getMessage());
            return null;
        }
    }

    private void setCacheEvictionPolicy(String policy) {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.setConfig("maxmemory-policy", policy);
            return null;
        });
        System.out.println("Política de remoção de cache ajustada para: " + policy);
    }

    private void setDefaultExpirationTime(int seconds) {
        redisTemplate.expire("exampleCache", Duration.ofSeconds(seconds));
        System.out.println("Tempo de expiração padrão ajustado para: " + seconds + " segundos.");
    }
}