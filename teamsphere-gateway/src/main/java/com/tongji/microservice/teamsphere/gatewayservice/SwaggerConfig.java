package com.tongji.microservice.teamsphere.gatewayservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        security = @SecurityRequirement(name="sa-token")
)
@SecurityScheme(type = SecuritySchemeType.HTTP,name = "sa-token",scheme = "bearer",in= SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .maxAge(3600)
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TeamSphere API")
                        .description("TeamSphere API implemented with Spring Boot RESTful service")
                        .version("v0.0.1"));
    }
}
