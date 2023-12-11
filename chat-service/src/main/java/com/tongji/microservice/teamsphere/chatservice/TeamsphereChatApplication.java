package com.tongji.microservice.teamsphere.chatservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class TeamsphereChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereChatApplication.class, args);
    }

}
