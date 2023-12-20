package com.tongji.microservice.teamsphere.projectservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereProjectApplication.class, args);
    }

}