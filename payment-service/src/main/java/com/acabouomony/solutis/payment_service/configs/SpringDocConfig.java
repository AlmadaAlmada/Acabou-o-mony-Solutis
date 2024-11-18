package com.acabouomony.solutis.payment_service.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Acabou o Mony - Payment API")
                        .description("API Rest contendo as funcionalidades de processamento e detalhamento de pagamento.")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("acabouomony@gmail.com")))
                .servers(List.of(
                        new Server().url("/payment-service").description("Payment Service")
                ));
    }
}
