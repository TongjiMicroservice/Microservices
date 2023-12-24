package com.tongji.microservice.teamsphere.scheduleservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.tongji.microservice.teamsphere.scheduleservice.mapper")
public class TeamsphereScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereScheduleApplication.class, args);
    }

}
