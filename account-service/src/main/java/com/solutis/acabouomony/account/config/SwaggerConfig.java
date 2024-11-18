package com.solutis.acabouomony.account.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("Account Service API")
                        .version("1.0")
                        .description("API para gestão de contas e autenticação do Acabou o Mony")
                        .termsOfService("https://acabouomony.com/termos-de-uso")
                        .contact(new Contact()
                                .name("Suporte Account Service")
                                .email("suporte@acabouomony.com")
                                .url("https://acabouomony.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .addSecurityItem(securityRequirement)
                .schemaRequirement("bearerAuth", securityScheme)
                .servers(List.of(
                        new Server().url("/account-service").description("Account Service")
                ));
    }
}
