package com.tongji.microservice.teamsphere.gatewayservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereGatewayApplication.class, args);
    }

}
