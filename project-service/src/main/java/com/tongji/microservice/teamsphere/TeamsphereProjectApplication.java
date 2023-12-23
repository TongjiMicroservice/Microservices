package com.tongji.microservice.teamsphere;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.tongji.microservice.teamsphere.projectservice.mapper")
public class TeamsphereProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamsphereProjectApplication.class, args);
    }

}
