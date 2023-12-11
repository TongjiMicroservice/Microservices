package com.tongji.microservice.teamsphere;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TeamSphere API")
                        .description("TeamSphere API implemented with Spring Boot RESTful service")
                        .version("v0.0.1"));
    }
}
