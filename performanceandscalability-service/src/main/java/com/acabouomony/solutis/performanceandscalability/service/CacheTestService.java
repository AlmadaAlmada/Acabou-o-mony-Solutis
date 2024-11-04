package com.acabouomony.solutis.performanceandscalability.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheTestService {

    private static final Logger logger = LoggerFactory.getLogger(CacheTestService.class);

    @Cacheable("exampleCache")
    public String getExampleData(String param) {
        // Log informativo ao iniciar o método
        logger.info("Iniciando o método getExampleData com parâmetro: {}", param);

        // Processamento simulado de dados
        String data = "Dados processados para " + param;

        // Log de depuração para verificar o conteúdo dos dados gerados
        logger.debug("Dados gerados: {}", data);

        return data;
    }
}