package com.tongji.microservice.teamsphere.scheduleservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereScheduleApplication.class, args);
    }

}
