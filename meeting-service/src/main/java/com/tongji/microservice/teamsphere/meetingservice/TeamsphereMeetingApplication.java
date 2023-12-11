package com.tongji.microservice.teamsphere.meetingservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereMeetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereMeetingApplication.class, args);
    }

}
