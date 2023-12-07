package com.tongji.microservice.teamsphere;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereTaskApplication.class, args);
    }

}
