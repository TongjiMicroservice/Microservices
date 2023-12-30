package com.tongji.microservice.teamsphere.fileservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.tongji.microservice.teamsphere.fileservice.mapper")
public class TeamsphereFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereFileApplication.class, args);
    }

}
